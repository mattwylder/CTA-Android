package us.wylder.cta.data;

/**
 * Created by mattwylder on 11/21/14.
 */
public class EtaObject {
    public String destName;
    public String eta;
    public String now;
    public EtaObject(String dest, String eta, String now)
    {
        this.destName = dest;
        this.eta = eta;
        this.now = now;
    }

}
