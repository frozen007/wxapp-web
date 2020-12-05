package com.myz.wxapp.ticket.service;

import com.myz.wxapp.ticket.Ticket;
import com.myz.wxapp.ticket.dao.TicketDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * TicketService
 * Created by myz
 * Date 2020/12/1 15:00
 */
@Service
public class TicketService {

    private static final Logger logger = LoggerFactory.getLogger(TicketService.class);

    private final ConcurrentHashMap<String, ReentrantLock> lockMaps = new ConcurrentHashMap<>();

    private Map<String, Ticket> localTicketMap = new HashMap<>();

    @Autowired
    private TicketDao ticketDao;

    public TicketService() {
        init();
    }

    private void init() {

    }

    private Ticket reloadTicket(String ticketName) throws Exception {
        Ticket newTicket = new Ticket();
        int reloadCnt = 0;

        while (true) {
            //select from storage
            Ticket ticketAvailable = ticketDao.getByName(ticketName);

            if (ticketAvailable == null) {
                // ticket not exists
                throw new Exception("ticket not exists: " + ticketName);
            }


            newTicket.setCurrentId(ticketAvailable.getCurrentId());
            newTicket.setMaxId(ticketAvailable.getCurrentId() + ticketAvailable.getStep());
            newTicket.setName(ticketName);

            //update ticket in storage to an available state for next reload
            long newCurrentId = newTicket.getMaxId();
            long preCurrentId = ticketAvailable.getCurrentId();
            int updCnt = ticketDao.updateTicketForSync(ticketName, newCurrentId, preCurrentId);
            if (logger.isDebugEnabled()) {
                logger.debug("update ticket for sync: ticketName={}, newCurrentId={}, preCurrentId={}",
                        ticketName, newCurrentId, preCurrentId);
            }
            if (updCnt == 1) {
                break;
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("update ticket for sync retrying: ticketName={}, newCurrentId={}, preCurrentId={}",
                            ticketName, newCurrentId, preCurrentId);
                }
                try {
                    Thread.sleep(30L);
                } catch (InterruptedException e) {
                    //pass InterruptedException to outer code
                    Thread.currentThread().interrupt();
                }
                reloadCnt++;
                if (reloadCnt >= 10) {
                    logger.error("ticket reload failed: ticketName={}, newCurrentId={}, preCurrentId={}",
                            ticketName, newCurrentId, preCurrentId);
                    throw new RuntimeException("ticket reload failed.");
                }
            }
        }

        return newTicket;
    }

    public long[] batchFetch(String ticketName, int fetchCount) throws Exception {

        //return 0 when abnormal case
        if (fetchCount <= 0) {
            return new long[0];
        }

        ReentrantLock lock = lockMaps.get(ticketName);
        if (lock == null) {
            lockMaps.putIfAbsent(ticketName, new ReentrantLock());
            lock = lockMaps.get(ticketName);
        }

        try {
            lock.tryLock(3, TimeUnit.SECONDS);

            Ticket ticket = localTicketMap.get(ticketName);

            if (ticket == null) {
                ticket = reloadTicket(ticketName);
                localTicketMap.put(ticketName, ticket);
            }

            long[] result = new long[fetchCount];
            long currentId = ticket.getCurrentId();
            long maxId = ticket.getMaxId();
            for (int i = 0; i < fetchCount; i++) {
                result[i] = currentId;
                currentId++;
                if (currentId == maxId) {
                    //reach the local maxId, need reload from storage
                    ticket = reloadTicket(ticketName);
                    localTicketMap.put(ticketName, ticket);
                    currentId = ticket.getCurrentId();
                    maxId = ticket.getMaxId();
                }
            }

            //update current id in local map
            ticket.setCurrentId(currentId);

            return result;
        } catch (InterruptedException e) {
            logger.error("Interrupted when get ticket", e);
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
        return null;
    }

    public Ticket lookup(String ticketName) {
        return localTicketMap.get(ticketName);
    }


}

