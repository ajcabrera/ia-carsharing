import IA.Comparticion.Usuario;
import IA.Comparticion.Usuarios;

public class Start {

    public static void main(String[] args) {
        Usuarios U = new Usuarios(30,3,1);
        int user = 0;
        for( Usuario u : U ){
            System.out.println(user + "\nDestino X : "+ u.getCoordDestinoX());
            System.out.println("Destino Y : "+ u.getCoordDestinoY());
            System.out.println("Origen X : "+ u.getCoordOrigenX());
            System.out.println("Origen Y : "+ u.getCoordOrigenY());
            System.out.println("Conductor? : "+ u.isConductor());
            user++;
        }
    }

}
