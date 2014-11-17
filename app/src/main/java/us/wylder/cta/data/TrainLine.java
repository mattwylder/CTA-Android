package us.wylder.cta.data;

import java.util.ArrayList;

/**
 * Created by mattwylder on 11/14/14.
 */
public class TrainLine {
    private String name;
    private int staId;
    private ArrayList<TrainStation> stations;

    //TODO: Array of stations

    public TrainLine(String color, TrainStation... stations)
    {
        this.name = color + " Line";
        this.stations = new ArrayList<TrainStation>();
        for(TrainStation curStation: stations)
        {
            this.stations.add(curStation);
        }
    }
}
