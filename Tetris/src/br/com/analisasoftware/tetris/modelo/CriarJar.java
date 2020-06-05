/*
 *Manipula arquivos Jar
 *Por: David de Almeida Bezerra J�nior
 *Vers�o: 1.3
 *Data: 24 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package br.com.analisasoftware.tetris.modelo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

public class CriarJar {
	//Cria arquivo Jar e compacta arquivos ao seu conte�do
    public static void criarArquivoJAR(File arquivoJAR, File[] sources, String arquivoMain, String diretorioProjeto, File[] fileTetrisWorkspace) throws IOException {
    	//Define arquivo manifest
        Manifest manifest = new Manifest();
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        manifest.getMainAttributes().put(Attributes.Name.MAIN_CLASS, arquivoMain);
        //Inicializa vari�vel de Streaming, setando o caminho do arquivo Jar que ser� gerado
        JarOutputStream jarOut = new JarOutputStream(new FileOutputStream(arquivoJAR), manifest);
        //Percorre o vetor com os arquivos que ser�o compactados
        for (int i = 0; i < sources.length; i++) {
            if (sources[i] == null || !sources[i].exists()) {
                continue;
            }

            System.out.println("Adicionando " + sources[i].getPath());
            //Adiciona elemento na posi��o i ao arquivo Jar
            adicionar(sources[i], jarOut, diretorioProjeto);
        }
        //Percorre o vetor com os arquivos padr�es do TetrisIDE
        for (int i = 0; i < fileTetrisWorkspace.length; i++) {
            if (fileTetrisWorkspace[i] == null || !fileTetrisWorkspace[i].exists()) {
                continue;
            }

            System.out.println("Adicionando " + fileTetrisWorkspace[i].getPath());
            //Adiciona elemento na posi��o i ao arquivo Jar
            adicionar(fileTetrisWorkspace[i], jarOut, System.getProperty("user.home")+System.getProperty("file.separator")+"TetrisWorkspace");
        }
        //Fecha vari�vel de Streaming
        jarOut.close();
        System.out.println("JAR conclu�do!");
    }

    //Adiciona arquivo � Streaming de arquivo Jar
    public static void adicionar(File source, JarOutputStream jarOut, String diretorioProjeto) throws IOException {
    	//Cria vari�vel de Streaming
        BufferedInputStream in = null;
        //Vari�vel de aux�lio ao caminho do arquivo
        String name = source.getPath().replace("\\", "/").replace((diretorioProjeto+System.getProperty("file.separator")+"bin"+System.getProperty("file.separator")).replace("\\", "/"), "").replace((diretorioProjeto+System.getProperty("file.separator")+"comp"+System.getProperty("file.separator")).replace("\\", "/"), "").replace((diretorioProjeto+System.getProperty("file.separator")).replace("\\", "/"), "");
        
        try {
        	//Verifica se � um diret�rio
            if (source.isDirectory()) {
                //Verifica se n�o est� vazio
                if (!name.isEmpty()) {
                	//Caso n�o termine com /, adiciona ao final
                    if (!name.endsWith("/")) {
                        name += "/";
                    }
                }
                //Percorre a lista de arquivos presentes no diret�rio e chama este m�todo recursivamente
                for (File nestedFile : source.listFiles()) {
                    adicionar(nestedFile, jarOut, diretorioProjeto);
                }
                return;
            }
            //Vari�veis contendo a entrada do arquivo para o Jar e o Streaming de entrada
            JarEntry entry = new JarEntry(name);
            entry.setTime(source.lastModified());
            jarOut.putNextEntry(entry);
            in = new BufferedInputStream(new FileInputStream(source));
            //Vari�vel de aux�lio para a leitura de buffer
            byte[] buffer = new byte[1024];
            while (true) {
                int count = in.read(buffer);
                if (count == -1) {
                    break;
                }
                //Escreve o buffer do arquivo no arquivo Jar
                jarOut.write(buffer, 0, count);
            }
            //Fecha a entrada
            jarOut.closeEntry();
        } finally {
        	//Fecha o Streaming de entrada
            if (in != null) {
                in.close();
            }
        }
    }

    
}

