package domain;

import domain.contacto.Email;
import domain.contacto.MedioDeContacto;
import domain.contacto.Telefono;
import domain.contacto.Whatsapp;
import domain.usuarios.ColaboradorFisico;
import domain.usuarios.ColaboradorJuridico;
import domain.usuarios.Rubro;
import domain.usuarios.TipoRazonSocial;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ColaboradorJuridicoTests {
    private ColaboradorJuridico metrovias;
    private Email metroviasEmail;
    private Whatsapp metroWhastapp;


    @BeforeEach
    public void setUp() {
        this.metroviasEmail = new Email("metro@gmail.com");
        this.metrovias = new ColaboradorJuridico("Metrovias S.A", TipoRazonSocial.EMPRESA, Rubro.SERVICIOS,metroviasEmail);
        this.metroWhastapp = new Whatsapp("+549116574460");
    }

    @Test
    @DisplayName("Tiene que tener al menos 1 medio de contacto")
    public  void testCrearColaboradorSinMediosDeContacto(){

        assertThrows(Exception.class, () -> new ColaboradorJuridico("Metrovias S.A", TipoRazonSocial.EMPRESA,Rubro.SERVICIOS,null));
    }

    @Test
    @DisplayName("No puede tener medios de contacto repetidos")
    public  void testCrearColaboradorConMediosDeContactoRepetidos(){
        metrovias.agregarMedioDeContacto(metroviasEmail);
        assertEquals(1,metrovias.getMediosDeContacto().size());
    }

    @Test
    @DisplayName("Puede tener solo 2 medios de contacto ")
    public  void testCrearColaboradorConDosMediosDeContacto(){

        metrovias.agregarMedioDeContacto(metroWhastapp);
        assertEquals(2,metrovias.getMediosDeContacto().size());
    }

    @Test
    @DisplayName("No puede tener mas de 3 medios de contacto ")
    public  void testCrearColaboradorConMasDeTresMediosDeContacto(){
        Whatsapp otroWhatsapp = new Whatsapp("+549114444666");
        Email otroEmail = new Email("otro@gmail.com");
        metrovias.agregarMedioDeContacto(otroEmail);
        metrovias.agregarMedioDeContacto(otroWhatsapp);
        assertThrows(Exception.class, () -> metrovias.agregarMedioDeContacto(otroEmail));
    }

    @Test
    @DisplayName("Tiene Razon Social, Tipo de Razon Social y un rubro  ")
    public  void testCrearColaborador(){
        //valida que el colaborador tenga los datos correctos
        assertEquals("Metrovias S.A",metrovias.getRazonSocial());
        assertEquals(TipoRazonSocial.EMPRESA,metrovias.getTipoRazonSocial());
        assertEquals(Rubro.SERVICIOS,metrovias.getTipoDeRubro());
    }

    @Test
    @DisplayName("Puede ser dado de baja ")
    public  void testBajaColaborador(){
        metrovias.darDeBaja();
        assertEquals(false,metrovias.getActivo());
    }

    @Test
    @DisplayName("Puede ser dado de alta ")
    public  void testAltaColaborador(){
        assertEquals(true,metrovias.getActivo());
    }

    @Test
    @DisplayName("Puede modificarse su informacion")
    public  void testModificarColaborador(){
        metrovias.setRazonSocial("Metrovias S.A Modificado");
        metrovias.setTipoRazonSocial(TipoRazonSocial.ONG); //Modificado
        metrovias.setTipoDeRubro((Rubro.SALUD));//Modificado

        assertEquals("Metrovias S.A Modificado",metrovias.getRazonSocial());
        assertEquals(TipoRazonSocial.ONG,metrovias.getTipoRazonSocial());
        assertEquals(Rubro.SALUD,metrovias.getTipoDeRubro());

    }

}
