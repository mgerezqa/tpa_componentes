package scheduler;

import config.ServiceLocator;
import controladores.ControladorReportes;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class CrearReporteJob implements Job {
    private final ControladorReportes controladorReportes = ServiceLocator.instanceOf(ControladorReportes.class);
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Creando reportes...");
        controladorReportes.creacionAutomatica();
        System.out.println("Reportes creados.");
    }
}
