package edu.escuelaing.arep.nanoSpring.Controllers;

import edu.escuelaing.arep.nanoSpring.Entities.Customer;
import edu.escuelaing.arep.nanoSpring.NanoSpringBoot.RequestMapping;
import edu.escuelaing.arep.nanoSpring.Persistence.CustomerDAO;

/**
 *
 * @author Benavides(Modify by Alan Marin)
 */
public class NanoSpringBootWeb{

    @RequestMapping("/customers")
    public static String index(){
        return  getDataChange();
    }

    /**
     * From the database show all data
     * @return String
     */
    public static String getDataChange(){
        String list = "";
        for (Customer c:CustomerDAO.getInstance().getData()){
            list += c.getName()+"#"+c.getNumber()+"%";
        }
        return list;
    }

}
