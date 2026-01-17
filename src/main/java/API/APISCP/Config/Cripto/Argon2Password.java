package API.APISCP.Config.Cripto;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Service;

@Service
public class Argon2Password {
    private static final int ITERATIONS = 10;
    private static final int MEMORY = 32768;
    private static final int PARALLELISM = 2;

    //Crear instancia de Argon2id
    private Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

    /**
     * Recibe el parametro password y encripta utilizando los valores definidos en la clase
     * @param contrasenia sin encriptar
     * @return, retorna una cadena basada en Argon2id
     */
    public String EncriptarContrasenia(String contrasenia){
        return argon2.hash(ITERATIONS, MEMORY, PARALLELISM, contrasenia);
    }

    /**
     * El metodo recibe ambas contrasenas y mediante el metodo verify de Argon2, evalua si la contrasena es correcta.
     * @param contraseniaBD password proveniente de la base de datos
     * @param contrasenia password sin encriptacion, es el valor que el usuario ingresa en el login
     * @return
     */
    public boolean verificarContrasenia(String contraseniaBD, String contrasenia){
        return argon2.verify(contraseniaBD, contrasenia);
    }
}
