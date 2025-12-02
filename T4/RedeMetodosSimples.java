import java.util.Scanner;

public class RedeMetodosSimples {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String entrada = sc.nextLine();

        String ip = pegarIP(entrada);
        int mascara = pegarMascara(entrada);

        int[] partes = separarIP(ip);

        int[] rede = calcularRede(partes, mascara);
        int[] broadcast = calcularBroadcast(partes, mascara);

        int primeiroHost = rede[3] + 1;
        int ultimoHost = broadcast[3] - 1;

        System.out.println("Rede: " + formatarIP(rede));
        System.out.println("Broadcast: " + formatarIP(broadcast));
        System.out.println("Host: " + rede[0] + "." + rede[1] + "." + rede[2] + "." + primeiroHost + " a " + broadcast[0] + "." + broadcast[1] + "." + broadcast[2] + "." + ultimoHost);
    }

    // Retorna a parte do IP antes da barra
    public static String pegarIP(String entrada) {
        return entrada.split("/")[0];
    }

    // Retorna a máscara depois da barra
    public static int pegarMascara(String entrada) {
        //Pega a parte apos a barra e transforma em número inteiro.
        return Integer.parseInt(entrada.split("/")[1]);
    }

    // Separa IP em 4 octetos
    public static int[] separarIP(String ip) {
        String[] partes = ip.split("\\.");
        return new int[]{
                Integer.parseInt(partes[0]),
                Integer.parseInt(partes[1]),
                Integer.parseInt(partes[2]),
                Integer.parseInt(partes[3])
        };
    }

    // Calcula rede
    public static int[] calcularRede(int[] p, int mascara) {
        int ipInt = ipParaInt(p);
        int maskInt = mascaraParaInt(mascara);
        return intParaIp(ipInt & maskInt);
    }

    // Calcula broadcast 
    public static int[] calcularBroadcast(int[] p, int mascara) {
        int ipInt = ipParaInt(p);//converte esse IP para um inteiro de 32 bits
        int maskInt = mascaraParaInt(mascara);
        //transforma todos os bits de host em 1
        return intParaIp(ipInt | ~maskInt);
    }

    // Converte A.B.C.D para inteiro 32 bits
    public static int ipParaInt(int[] p) {
        return (p[0] << 24) | (p[1] << 16) | (p[2] << 8) | p[3];
    }

    // Converte máscara CIDR /m para inteiro 32 bits
    public static int mascaraParaInt(int m) {
        return (int)(0xFFFFFFFF << (32 - m));
    }

    // Converte inteiro 32 bits de volta para A.B.C.D
    public static int[] intParaIp(int valor) {
        return new int[]{
                (valor >>> 24) & 0xFF,
                (valor >>> 16) & 0xFF,
                (valor >>> 8) & 0xFF,
                valor & 0xFF
        };
    }

    // Formata A.B.C.D em string
    public static String formatarIP(int[] p) {
        return p[0] + "." + p[1] + "." + p[2] + "." + p[3];
    }
}

