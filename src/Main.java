
import java.util.Scanner;//clase para leitura de valores de entrada
import java.io.BufferedReader;//classe para ler o arquivo CSV
import java.io.FileNotFoundException;//classe para tratar exceções de arquivo não encontrado
import java.io.FileReader;//classe para ler arquivos
import java.io.IOException;//classe para tratar exceções de IO
import java.util.ArrayList;//clase para utilizar um ArrayList
import java.util.Collections;//classe para order o ArrayList

public class Main {

    public static void main(String[] args) {
        
        //declaração de variáveis
        
        //instância objeto para entrada de dados
        Scanner leia = new Scanner(System.in);
        
        //criação de um ArrayList para guardar uma lista de objetos
        ArrayList<PontoTaxi> listaDeTaxis = new ArrayList();
        double latitude = 0;
        double longitude = 0;
        
        int opcao;
        
        //laço de repetição principal
        do {
            
            //legenda do menu de escolha
            System.out.println("=============MENU=============\n"
                    + "1. Listar todos os pontos de taxi\n"
                    + "2. Informar minha localização\n"
                    + "3. Encontrar pontos próximos\n"
                    + "4. Buscar pontos por logradouros\n"
                    + "5. Terminar programa\n\n"
                    + "Escolha uma das opções: ");
            
            //armazena a opção do usuário
            opcao = leia.nextInt();
            
            //estrutura condicional das opções
            switch(opcao){
                case 1:
                    //lê, armazena e imprime os dados do arquivo CSV
                    
                    //variável que especifica o caminho do arquivo
                    String csvArquivo = "D:\\Estudos\\Java\\ExercicoDell\\src\\pontos_taxi.csv";
                    
                    //variável do tipo BufferedReader usada para ler arquivos
                    BufferedReader conteudoCSV = null;
                    
                    //variável auxiliar
                    String linha = "";

                    //delimitador de campos do CSV
                    String csvSeparadorCampo = ";";

                    //bloco try para evitar exceções
                    try {

                        //instanciação do objeto BufferReader
                        conteudoCSV = new BufferedReader(new FileReader(csvArquivo));
                        
                        
                        //laço de repetição para ler as linhas do arquivo
                        while ((linha = conteudoCSV.readLine()) != null) {
                            
                            //Array que vai receber o retorno da função split
                            String[] pontosTaxi = linha.split(csvSeparadorCampo);
                            
                            //instância o objeto da classe PontoTaxi
                            PontoTaxi objTaxi = new PontoTaxi();
                            //atribundo valores
                            objTaxi.codigo = pontosTaxi[1];
                            objTaxi.nome = pontosTaxi[2];
                            objTaxi.telefone = pontosTaxi[3];
                            objTaxi.logradouro = pontosTaxi[4];
                            objTaxi.numero = pontosTaxi[5];
                            objTaxi.latitude = Double.parseDouble(pontosTaxi[6].replace(",", "."));
                            objTaxi.longitude = Double.parseDouble(pontosTaxi[7].replace(",", "."));
                            
                            //objeto está sendo adicionado ao ArrayList
                            listaDeTaxis.add(objTaxi);
          
                        }
                        //laço para percorrer e imprimir informações do ArrayList
                        for (int i = 0; i < listaDeTaxis.size(); i++) {
                         
                            System.out.println(listaDeTaxis.get(i).toString());
                        }
                    
                    //tratamentos de exceções
                    } catch (FileNotFoundException e){
                        System.out.println("Arquivo não encontrado" +e.getMessage());
                    
                    //tratamentos de exceções
                    } catch (IOException e) {
                        System.out.println("IO Erro: \n" +e.getMessage());
                    
                    //bloco obrigatório mesmo em caso de exceções
                    } finally {
                        if (conteudoCSV != null) {
                            try {
                                conteudoCSV.close();
                            }catch (IOException e) {
                                System.out.println("I0 Erro: \n" +e.getMessage());
                            }
                        }
                    }
                break;
                
                case 2: 
                    //lê e armazena a localização do usuário
                    System.out.println("Informe sua localização\n"
                            + "Digite sua latitude: ");
                    latitude = leia.nextDouble();
                    
                    System.out.println("Digite sua longitude: "); 
                    longitude = leia.nextDouble();
                    
                    System.out.println("Localização armazenada.");
                    
                break;
                
                case 3:
                    //calcula a distancia e mostra os 3 pontos mais próximo
                    //instânciando o ArrayList
                    ArrayList<Double> listaDistancia = new ArrayList();
                    
                    //laço que calcula a distância entre cada ponto a cada iteração
                    for (int i = 0; i < listaDeTaxis.size(); i++) {
                        listaDeTaxis.get(i).distancia = Haversine.haversine(latitude, longitude, listaDeTaxis.get(i).latitude, listaDeTaxis.get(i).longitude);
                        
                        //adicionando a distância calculada a uma lista separada
                        listaDistancia.add(listaDeTaxis.get(i).distancia);
                    }
                    
                    //ordenando a lista de forma crescente
                    Collections.sort(listaDistancia);
                    
                    //percorrendo a lista e relacionando os valores
                    for (int i = 0; i < listaDeTaxis.size(); i++) {
                        if (listaDeTaxis.get(i).distancia == listaDistancia.get(0)) {
                            System.out.println("PONTO MAIS PRÓXIMO: \n" + listaDeTaxis.get(i).toString() + "\n DISTÂNCIA: " + listaDeTaxis.get(i).distancia);
                        }
                    }
                    //percorrendo a lista e relacionando os valores
                    for (int i = 0; i < listaDeTaxis.size(); i++) {
                        if (listaDeTaxis.get(i).distancia == listaDistancia.get(1)) {
                            System.out.println("SEGUNDO PONTO MAIS PRÓXIMO: \n" + listaDeTaxis.get(i).toString() + "\n DISTÂNCIA: " + listaDeTaxis.get(i).distancia);
                            break;
                        }
                    }
                    //percorrendo a lista e relacionando os valores                   
                    for (int i = 0; i < listaDeTaxis.size(); i++) {
                        if (listaDeTaxis.get(i).distancia == listaDistancia.get(2)) {
                            System.out.println("TERCEIRO PONTO MAIS PRÓXIMO: \n" + listaDeTaxis.get(i).toString() + "\n DISTÂNCIA: " + listaDeTaxis.get(i).distancia);
                            break;
                        }
                    }
                break;
                case 4:
                    //pesquisar o endereço dentro da listaDeTaxis
                    System.out.println("Digite o endereço: ");
                    leia.nextLine();
                    String endereco = leia.nextLine();
                    
                    //percorre a lista
                    for (int i = 0; i < listaDeTaxis.size(); i++) {
                        //compara o endereço digitado com o ponto atual
                        //ignorando maiusculas e minisculas
                        if (endereco.equalsIgnoreCase(listaDeTaxis.get(i).logradouro)){
                            System.out.println(listaDeTaxis.get(i).toString());
                        }
                    }
                break;
                
                case 5:
                    //terminar o programa
                    System.out.println("Saindo...");
                    System.exit(0);
                    
                break;
                
                default:
                    //mensagem caso o valor digitado seja inválido
                    System.out.println("Valor inválido! Digite um valor válido.");
                break;
            
            }   
            
         //fim do laço principal
        } while (opcao != 5);
    }
    
}
