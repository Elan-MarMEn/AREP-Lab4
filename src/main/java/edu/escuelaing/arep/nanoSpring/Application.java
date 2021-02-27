package edu.escuelaing.arep.nanoSpring;

import edu.escuelaing.arep.nanoSpring.NanoSpringBoot.NanoSpringBootApp;

public class Application {
    public static void main(String[] args) {
        String[] comp = {"edu.escuelaing.arep.nanoSpring.Controllers.NanoSpringBootWeb"};
        NanoSpringBootApp.run(comp);
    }
}
