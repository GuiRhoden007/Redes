import java.util.Scanner;

public class RedeMetodosSimples {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Pede para o usuário digitar o IP/máscara
        System.out.print("Digite o IP/máscara (ex: 192.168.10.50/24): ");
        String entrada = sc.nextLine();
        sc.close();

        // Extrai apenas o IP (ex: de "192.168.10.50/24" pega só "192.168.10.50")
        String ip = pegarIP(entrada);

        // Extrai apenas a máscara (ex: de "192.168.10.50/24" pega 24)
        int mascara = pegarMascara(entrada);

        // Divide o IP em 4 partes, sem usar split
        // Exemplo: "192.168.10.50" vira: [192, 168, 10, 50]
        int[] partes = separarIP(ip);

        // Calcula o endereço da rede baseado na máscara
        int[] rede = calcularRede(partes, mascara);

        // Calcula o broadcast baseado na máscara
        int[] broadcast = calcularBroadcast(partes, mascara);

        // Primeiro host é o endereço da rede + 1 no último octeto
        int primeiroHost = rede[3] + 1;

        // Último host é o broadcast - 1 no último octeto
        int ultimoHost = broadcast[3] - 1;

        // Mostra tudo formatado
        System.out.println("Rede: " + formatarIP(rede));
        System.out.println("Broadcast: " + formatarIP(broadcast));
        System.out.println("Hosts: de " +
                rede[0] + "." + rede[1] + "." + rede[2] + "." + primeiroHost +
                " até " +
                broadcast[0] + "." + broadcast[1] + "." + broadcast[2] + "." + ultimoHost);
    }

    // Método para pegar apenas o IP antes da barra "/"
    public static String pegarIP(String entrada) {
        String ip = "";
        // Caminha por cada letra da entrada
        for (int i = 0; i < entrada.length(); i++) {
            // Quando encontrar a barra, parar
            if (entrada.charAt(i) == '/')
                break;
            // Se não, adicionar o caractere ao IP
            ip += entrada.charAt(i);
        }
        return ip;
    }

    // Método para pegar apenas a máscara depois da "/"
    public static int pegarMascara(String entrada) {
        String masc = "";
        boolean barra = false; // controla quando achar a barra

        for (int i = 0; i < entrada.length(); i++) {
            char c = entrada.charAt(i);

            // Quando achar a barra "/", ativa a flag
            if (c == '/') {
                barra = true;
            }
            // Depois da barra, começa a montar o número da máscara
            else if (barra) {
                masc += c;
            }
        }
        return Integer.parseInt(masc); // converte para número
    }

    // Método para separar o IP em 4 números: p1,p2,p3,p4
    public static int[] separarIP(String ip) {
        int[] p = new int[4]; // array com 4 posições
        String num = ""; // guarda cada parte temporária
        int indice = 0; // controla em qual posição do array estamos

        for (int i = 0; i < ip.length(); i++) {
            char c = ip.charAt(i);

            // Quando achar um ponto, salva o número montado
            if (c == '.') {
                p[indice] = Integer.parseInt(num);
                num = ""; // limpa para começar outro
                indice++; // pula para o próximo octeto
            } else {
                // vai montando o número
                num += c;
            }
        }

        // salva o último número (porque não termina com ponto)
        p[indice] = Integer.parseInt(num);

        return p;
    }

    // Método para calcular o endereço da rede
    public static int[] calcularRede(int[] p, int mascara) {
        // Cria uma cópia do IP
        int[] r = { p[0], p[1], p[2], p[3] };

        // Rede depende da máscara
        if (mascara == 24) {
            // Exemplo: 192.168.10.50 → 192.168.10.0
            r[3] = 0;
        } else if (mascara == 16) {
            r[2] = 0;
            r[3] = 0;
        } else if (mascara == 8) {
            r[1] = 0;
            r[2] = 0;
            r[3] = 0;
        }

        return r;
    }

    // Método para calcular o broadcast
    public static int[] calcularBroadcast(int[] p, int mascara) {
        // Cria cópia do IP
        int[] b = { p[0], p[1], p[2], p[3] };

        if (mascara == 24) {
            b[3] = 255;
        } else if (mascara == 16) {
            b[2] = 255;
            b[3] = 255;
        } else if (mascara == 8) {
            b[1] = 255;
            b[2] = 255;
            b[3] = 255;
        }

        return b;
    }

    // Método para transformar array de 4 números em IP tipo "A.B.C.D"
    public static String formatarIP(int[] p) {
        return p[0] + "." + p[1] + "." + p[2] + "." + p[3];
    }
}
