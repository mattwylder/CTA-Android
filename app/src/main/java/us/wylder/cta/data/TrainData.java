package us.wylder.cta.data;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.io.File;

public class TrainData{

    private  ArrayList<TrainStation> stations;

    public void parseFile()
    {
        try{
            Scanner in = new Scanner(new File("../../../../../res/stops.txt"));
            String str;
            String[] data;
            stations = new ArrayList<TrainStation>();

            while(in.hasNext())
            {
                str = in.nextLine();
                data = str.split(",");
                stations.add(new TrainStation(
                        Integer.parseInt(data[0]),
                        data[2],
                        Double.parseDouble(data[5]),
                        Double.parseDouble(data[6])
                    )
                );
            }

        }
        catch(FileNotFoundException e)
        {

        }
    }
    public void makeRoutes()
    {
        Iterator itr = stations.iterator();
        while(itr.hasNext())
        {

        }
    }
}