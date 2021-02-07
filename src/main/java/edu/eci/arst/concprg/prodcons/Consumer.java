/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arst.concprg.prodcons;

import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hcadavid
 */
public class Consumer extends Thread{
    
    private Queue<Integer> queue;
    private Producer p;
    
    
    public Consumer(Queue<Integer> queue){
        this.queue=queue;
        p = new Producer(queue,100);
        p.start();
    }
    
    @Override
    public void run() {
        while (true) {
            synchronized (p){
                try{
                    p.wait();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }

            while (queue.size() > 0) {
                int elem=queue.poll();
                System.out.println("Consumer consumes "+elem);                                
            }
            try {
                Thread.sleep(1500);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

        }
    }
}
