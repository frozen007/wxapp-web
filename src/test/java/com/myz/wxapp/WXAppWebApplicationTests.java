package com.myz.wxapp;

import com.myz.wxapp.ticket.service.TicketService;
import com.myz.wxapp.util.JsonKit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

@SpringBootTest
class WXAppWebApplicationTests {

	@Autowired
	private TicketService ticketService;

	@Autowired
	private JsonKit jsonKit;

	@Test
	void test001() throws Exception {
		int n=2;
		CountDownLatch latch = new CountDownLatch(n);
		Thread[] ts = new Thread[n];
		Random random = new Random(System.currentTimeMillis());
		for (int i = 0; i < ts.length; i++) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(Math.abs(random.nextLong())%3000);
						long[] rs = ticketService.batchFetch("test", 9);
						for (long r : rs) {
							System.out.println(Thread.currentThread().getName() + "," + r);
						}

						latch.countDown();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			t.setName("t" + i);
			ts[i] = t;
		}

		for (Thread t : ts) {
			t.start();
		}

		latch.await();
		System.out.println(jsonKit.toJson(ticketService.lookup("test")));
	}

}
