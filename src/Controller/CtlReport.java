
package Controller;

import Classes.ClassReports;
import Model.ModelReport;
import java.util.List;

/**
 *
 * @author wtria
 */
public class CtlReport {
    
    ModelReport modelReport;
    
    
    public CtlReport() {
        modelReport = new ModelReport();
    }
    
    public List<ClassReports> ListByPersonType(){
        return modelReport.ListByPersonType();
    }
    
    public List<ClassReports> ListByClientType(){
        return modelReport.ListByClientType();
    }
    
    public List<ClassReports> ListByFlightType(){
        return modelReport.ListByFlightType();
    }
    
}
