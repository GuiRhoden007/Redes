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
        int[] faixaInicio = new int[]{rede[0], rede[1], rede[2], primeiroHost};
        int[] faixaFim = new int[]{broadcast[0], broadcast[1], broadcast[2], ultimoHost};

        int qtdHosts = (int) Math.pow(2, 32 - mascara) - 2;

        System.out.println("Endereço IP (binário)    : " + formatarIPBinario(partes));
        System.out.println("Máscara (prefixo)        : /" + mascara);
        System.out.println("Máscara (decimal)        : " + formatarIP(intParaIp(mascaraParaInt(mascara))));
        System.out.println("Máscara (binário)        : " + formatarIPBinario(intParaIp(mascaraParaInt(mascara))));
        System.out.println("Endereço da rede         : " + formatarIP(rede));
        System.out.println("Endereço de broadcast    : " + formatarIP(broadcast));
        System.out.println("Quantidade de hosts      : " + qtdHosts);
        System.out.println("Faixa de hosts           : " + formatarIP(faixaInicio) + " a " + formatarIP(faixaFim));
    }

    // Retorna a parte do IP antes da barra
    public static String pegarIP(String entrada) {
        return entrada.split("/")[0];
    }

    // Retorna a máscara depois da barra
    public static int pegarMascara(String entrada) {
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
        int ipInt = ipParaInt(p);
        int maskInt = mascaraParaInt(mascara);
        return intParaIp(ipInt | ~maskInt);
    }

    // Converte A.B.C.D para inteiro 32 bits
    public static int ipParaInt(int[] p) {
        return (p[0] << 24) | (p[1] << 16) | (p[2] << 8) | p[3];
    }

    // Converte mascara CIDR /m para inteiro 32 bits
    public static int mascaraParaInt(int m) {
        return (int) (0xFFFFFFFF << (32 - m));
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

    // Formata IP em binario com 8 bits por octeto
    public static String formatarIPBinario(int[] p) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            String binario = String.format("%8s", Integer.toBinaryString(p[i])).replace(' ', '0');
            sb.append(binario);
            if (i < 3) {
                sb.append(".");
            }
        }

        return sb.toString();
    }
}

