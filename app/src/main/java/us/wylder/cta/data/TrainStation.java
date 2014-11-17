package us.wylder.cta.data;

public class TrainStation{
    private int staId;
    private String name;
    private double lat;
    private double lon;

    public TrainStation(int staId, String name, double lat, double lon)
    {
        this.staId = staId;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }

    public String toString()
    {
        return name;
    }
}