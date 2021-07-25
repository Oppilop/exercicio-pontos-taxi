//abstração das informações do arquivo CSV
public class PontoTaxi {
    
    String codigo;
    String nome;
    String telefone;
    String logradouro;
    String numero;
    double latitude;
    double longitude;
    double distancia;

    @Override
    public String toString() {
        return "PontoTaxi{" + "codigo=" + codigo + ", nome=" + nome + ", telefone=" + telefone + ", logradouro=" + logradouro + ", numero=" + numero + ", latitude=" + latitude + ", longitude=" + longitude + '}';
    }

    
}
