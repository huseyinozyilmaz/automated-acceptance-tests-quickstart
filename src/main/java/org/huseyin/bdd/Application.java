package org.huseyin.bdd;

public class Application {
    
    public static void main(String[] args) {
        
        System.out.println("Running...");
        ReportMerger.getInstance().merge(args[0]);
    }
}
