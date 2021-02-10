/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;

/**
 *
 * @author hcadavid
 */
public class CountThread extends Thread{
    int a;
    int b;
    /**
     *
     * @param limite inferior
     * @param limite superior
     */
    public  CountThread(int a, int b){
        this.a=a;
        this.b=b;
    }
    /**
     * Clase que incia el proceso del contador
     */
    public void run(){
        int i=0;
        for(i=a;i<=b;i++){
            System.out.println(i);
        }
    }
    /**
     * Clase principal para iniciliazar el proceso
     */
    public static void main(String[] args){
        CountThread count= new CountThread(0,100);
        count.start();
    }

    
}
