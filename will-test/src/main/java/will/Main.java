package will;

import apriori4j.AnalysisResult;
import apriori4j.AprioriAlgorithm;
import apriori4j.Transaction;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        AssoRule ar=new AssoRule();
        /*Map<String,String> m=ar.rdfile("/home/will/r_pg/2015-01-05_cn.txt");*/
        Map<String,String> m=ar.pnappid();
        System.out.println(ar.cntdbsql(m));

        List<Transaction> transactions = ar.cntdbsql(m);
        System.out.println(transactions.size());
        Double minSupport = 0.1;
        Double minConfidence = 0.8;

        AprioriAlgorithm apriori = new AprioriAlgorithm(minSupport, minConfidence);
        AnalysisResult result = apriori.analyze(transactions);
        ar.wtfile(result);
    }
}