import java.io.*;
import java.util.Scanner;

public class ArquivoProduto {
    private RandomAccessFile arquivo;
    private String nomeArquivo;
    private String nomeIndice;
    private Indice indice;
    
    public ArquivoProduto(String nomeArquivo, String nomeIndice) throws IOException {
        this.nomeArquivo = nomeArquivo;
        this.nomeIndice = nomeIndice;

        arquivo = new RandomAccessFile(this.nomeArquivo, "rw");
        indice = new Indice(20, this.nomeIndice);

        if (arquivo.length() < 4) { // caso seja um arquivo vazio
            arquivo.writeInt(0);
            indice.apagar();
        }
    } // fim do ArquivoProduto()

    public int incluir(Produto p) throws IOException {
        arquivo.seek(0); // ir para o cabecalho
        int ultimoID = arquivo.readInt(); // ler o ultimo ID

        arquivo.seek(0); // ir para o cabecalho
        arquivo.writeInt(ultimoID + 1); // atualizar o ID do novo produto a ser incluido
        p.setId(ultimoID + 1); // setar o ID do novo produto
        
        arquivo.seek(arquivo.length()); // ir para o final do arquivo
        long endereco = arquivo.getFilePointer(); // pegar o endereco do novo arquivo
        
        byte[] b = p.toByteArray(); // pegar os atributos do produto e tranformar em um array de bytes
        arquivo.writeChar(' '); // lapide vazia
        arquivo.writeShort(b.length); // escrever o tamanho do novo produto
        arquivo.write(b); // escrever o novo produto
        
        indice.inserir(p.getId(), endereco); // inserir o novo produto no indice
        return p.getId();
    } // fim do incluir()

    public Produto buscar(int id) throws IOException {
        long endereco = indice.buscar(id); // buscar o endereco do produto no indice
        if (endereco == -1) { // caso a busca falhe
            return null;
        }

        arquivo.seek(endereco); // ir para o endereco
        arquivo.readChar(); // ler a lapide
        short tam = arquivo.readShort(); // ler o tamanho do produto (objeto)
        
        byte[] b = new byte[tam]; // criar o array de byte pra receber o produto
        arquivo.read(b); // ler o produto e salvar em b

        Produto p = new Produto();
        p.fromByteArray(b); // carregar o produto com os valores do array de byte
        
        return p; // returnar o produto
    } // fim do buscar()

    public boolean excluir(int id) throws IOException {
        long endereco = indice.buscar(id); // buscar o endereco do produto no indice
        if (endereco == -1) { // caso a busca falhe
            return false;
        }

        arquivo.seek(endereco); // ir para o endereco do arquivo
        arquivo.readChar(); // ler a lapide
        short tam = arquivo.readShort(); // ler o tamanho do produto (objeto)

        byte[] b = new byte[tam]; // criar o array de byte pra receber o produto
        arquivo.read(b); // ler o produto e salvar em b

        Produto p = new Produto();
        p.fromByteArray(b); // carregar o produto com os valores do array de byte

        System.out.println("\nPRODUTO A SER EXCLUIDO:\n" + p); // mostrar ao usuario o produto a ser excluido
        Scanner scanner = new Scanner(System.in);

        System.out.print("\nTem certeza que deseja excluir este produto?\n0 - NAO\n1 - SIM\n\nSua escolha: "); // solicitar confirmacao do usuario
        short op = scanner.nextShort(); // ler a resposta do usuario
        while (op != 0 && op != 1) {
            System.out.print("\nTem certeza que deseja excluir este produto?\n0 - NAO\n1 - SIM\n\nSua escolha: "); // solicitar confirmacao do usuario
            op = scanner.nextShort(); // ler a resposta do usuario
        }

        if (op == 1) { // caso confirmada a exclusao
            arquivo.seek(endereco); // ir para o endereco do arquivo
            arquivo.writeChar('*'); // marcar o campo lapide
            indice.excluir(id); // excluir o produto no indice
            return true;
        }
        else { // caso cancelada a exclusao
            return false;
        }
    } // fim do excluir()

    public boolean alterar(int id) throws IOException {
        long endereco = indice.buscar(id); // buscar o endereco do produto no indice
        if (endereco == -1) { // caso a busca falhe
            return false;
        }

        arquivo.seek(endereco); // ir para o endereco do arquivo
        arquivo.readChar(); // ler a lapide
        short tam = arquivo.readShort(); // ler o tamanho do produto (objeto)

        byte[] b = new byte[tam]; // criar o array de byte pra receber o produto
        arquivo.read(b); // ler o produto e salvar em b

        Produto p = new Produto();
        p.fromByteArray(b); // carregar o produto com os valores do array de byte

        System.out.println("\nPRODUTO A SER ALTERADO:\n" + p); // mostrar ao usuario o produto a ser alterado
        Scanner scanner = new Scanner(System.in);

        System.out.print("\nTem certeza que deseja alterar este produto?\n0 - NAO\n1 - SIM\n\nSua escolha: "); // solicitar confirmacao do usuario
        short op = scanner.nextShort(); // ler a resposta do usuario
        while (op != 0 && op != 1) {
            System.out.print("\nTem certeza que deseja alterar este produto?\n0 - NAO\n1 - SIM\n\nSua escolha: "); // solicitar confirmacao do usuario
            op = scanner.nextShort(); // ler a resposta do usuario
        }

        if (op == 1) { // caso confirmada a alteracao
            arquivo.seek(endereco); // ir para o endereco do arquivo
            arquivo.writeChar('*'); // marcar o campo lapide

            boolean saidapermitida = false;
            do {
                System.out.println("\nPor favor, digite os novos dados do produto.\n");
                try {
                    System.out.print("Nome: ");
                    scanner.nextLine();
                    String nome = scanner.nextLine();
                    System.out.print("Descricao: ");
                    String descricao = scanner.nextLine();
                    System.out.print("Preco: ");
                    float preco = scanner.nextFloat();
                    System.out.print("Tamanho: ");
                    float tamanho = scanner.nextFloat();
                    System.out.print("Peso: ");
                    float peso = scanner.nextFloat();
    
                    p = new Produto(nome, descricao, preco, tamanho, peso);
                    p.setId(id); // id se mantem
                    saidapermitida = true;
                }
                catch(Exception e) {
                    System.out.println("Entrada invalida. Por favor, tente novamente.");
                }
            } while (!saidapermitida);

            arquivo.seek(arquivo.length()); // ir para o final do arquivo
            endereco = arquivo.getFilePointer(); // pegar o endereco do "novo" arquivo

            byte[] b2 = p.toByteArray(); // pegar os atributos do produto e tranformar em um array de bytes
            arquivo.writeChar(' '); // lapide vazia
            arquivo.writeShort(b2.length); // escrever o tamanho do novo produto
            arquivo.write(b2); // escrever o novo produto

            indice.atualizar(id, endereco); // alterar o produto no indice
            return true;
        }
        else { // caso cancelada a alteracao
            return false;
        }
    } // fim do alterar()

    public short quantidade() throws IOException {
        char lapide;
        short tam, quant = 0;

        try {
            arquivo.seek(0); // voltar para o inicio do arquivo
            arquivo.readInt(); // pular o cabecalho

            while (true) {
                lapide = arquivo.readChar(); // ler a lapide
                tam = arquivo.readShort(); // ler o tamanho do objeto
                
                if (lapide == ' ') { // caso o produto exista
                    quant++;
                    arquivo.seek(arquivo.getFilePointer() + tam); // pular o registro
                }
                else { // caso o produto tenha sido excluido
                    arquivo.seek(arquivo.getFilePointer() + tam); // pular o registro excluido
                }
            } // fim do while
        }
        catch(Exception e) {
            return quant;
        }
    } // fim do quantidade()

    public void listar() throws IOException {
        char lapide;
        short tam;
        Produto p;
        byte[] b;

        try {
            arquivo.seek(0); // voltar para o inicio do arquivo
            arquivo.readInt(); // pular o cabecalho

            while (true) {
                lapide = arquivo.readChar(); // ler a lapide
                tam = arquivo.readShort(); // ler o tamanho do objeto
                p = new Produto();

                if (lapide == ' ') { // caso o produto exista
                    b = new byte[tam]; // criar o array de byte pra receber o produto
                    arquivo.read(b); // ler o produto e salvar em b
                    p.fromByteArray(b); // carregar o produto com os valores do array de byte
                    System.out.println(p + "\n"); // escrever na tela o produto
                }
                else { // caso o produto tenha sido excluido
                    arquivo.seek(arquivo.getFilePointer() + tam); // pular o registro excluido
                }
            } // fim do while
        }
        catch(Exception e) {
            System.out.println("---FIM DA LISTA---");
        }
    } // fim do listar()

    public void close() throws IOException {
        arquivo.close();
    } // fim do close()

} // fim da ArquivoProduto