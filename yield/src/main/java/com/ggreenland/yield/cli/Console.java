package com.ggreenland.yield.cli;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.ggreenland.yield.Bond;
import com.ggreenland.yield.LinearInterpolatedSpread;
import com.ggreenland.yield.SimpleSpread;
import com.ggreenland.yield.SpreadCalculator;
import com.ggreenland.yield.service.BondDataService;
import com.ggreenland.yield.service.BondServiceException;
import com.ggreenland.yield.service.LocalBondDataService;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Console {

    @Parameter(names={"--mode", "-m"},
            required= true,
            description = "0 - simple (nearest neighbour)\n 1 - linear interpolated")
    int mode = 0;
    @Parameter(description = "Input path", required = true)
    private List<String> filepaths = new ArrayList<String>();
    @Parameter(names = {"--help", "-h"}, help = true)
    private boolean help;


    public static void main(String ... args) throws FileNotFoundException {
        Console cnsl = new Console();
        JCommander cmdr = new JCommander(cnsl, args);
        if (cnsl.help){
            cmdr.usage();
            return;
        }
        cnsl.run();
    }

    private BondDataService getBondDataService() throws FileNotFoundException {
        return LocalBondDataService.fromJson(new File(filepaths.get(0)));
    }

    private SpreadCalculator getSpreadCalculator(BondDataService service){
        if (mode == 0)
            return new SimpleSpread(service);
        else if (mode == 1)
            return new LinearInterpolatedSpread(service);
        else
            throw new IllegalArgumentException("Mode must be 0 or 1");
    }

    private void calculateSpreads(BondDataService service, SpreadCalculator calculator) {

        List<Bond> corpBonds = service.find(Bond.Type.CORP);

        for (Bond b : corpBonds){
            System.out.println(b.name);
            try {
                double spread = calculator.calculate(b);
                System.out.println("spread = " + String.format("%.2f",spread));
            } catch (BondServiceException e) {
                System.out.println("Cannot calculate spread for bond " );
            }
        }

    }

    public void run() throws FileNotFoundException {
        BondDataService service = getBondDataService();
        SpreadCalculator calculator = getSpreadCalculator(service);
        calculateSpreads(service,calculator);
    }
}
