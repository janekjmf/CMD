/**
 * @author Janek Misioski | https://github.com/janekjmf 	| janek.misiorski@gmail.com 		| +48 883 483 807
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class cmd
{
    public static void main(String[] args) throws IOException
    {
        boolean EXIT = false;
        String Path = System.getProperty("user.home");
        System.out.println();
        System.out.println("W celu wyświetlenia informacji na temat poszczególnych komend wpisz \"HELP\"");
        Scanner scanner = new Scanner(System.in);
        while (!EXIT)
        {
            System.out.print(Path+">");
            String input = scanner.nextLine();
            input = input.toLowerCase();

            if(input.equalsIgnoreCase("EXIT"))
                EXIT = true;
            else if(input.equalsIgnoreCase("HELP"))
                HELP();
            else if(input.equalsIgnoreCase("CD"))
                System.out.println(Path);
            else if(input.equalsIgnoreCase("CHDIR"))
                System.out.println(Path);
            else if(input.startsWith("cd "))
                Path = CD(input, Path);
            else if(input.startsWith("chdir "))
                Path = CHDIR(input, Path);
            else if(input.equalsIgnoreCase("CD.."))
                Path = CDB(Path);
            else if(input.equalsIgnoreCase("CHDIR.."))
                Path = CDB(Path);
            else if(input.startsWith("mkdir "))
                MKDIR(input,Path);
            else if(input.startsWith("md "))
                MD(input,Path);
            else if(input.equalsIgnoreCase("DIR"))
                DIR(Path);
            else if(input.startsWith("del "))
                DEL(input,Path);
            else if(input.startsWith("rmdir "))
                RMDIR(input,Path);
            else if(input.startsWith("rename "))
                RENAME(input, Path);
            else if(input.startsWith("ren "))
                REN(input, Path);
            else if(input.equalsIgnoreCase("TREE"))
                TREE(Path, Path);
            else if(input.equalsIgnoreCase("TIME"))
                TIME();
            else if(input.equalsIgnoreCase("DATE"))
                DATE();
            else if(input.startsWith("type "))
                TYPE(input, Path);
            else if(input.startsWith("copy "))
                COPY(input, Path);
            else if(input.startsWith("move "))
                MOVE(input, Path);
            else if(input.length() == 2 && input.charAt(1) == ':')
                Path = zmianaDysku(input, Path);
            else if(input.equalsIgnoreCase("ver"))
                VER();
            else if(input.equalsIgnoreCase("shutdown"))
                SHUTDOWN();
            else
                System.out.println("Wprowadzono niepoprawną komendę.");
        }
        scanner.close();
    }

    public static void HELP()
    {
        System.out.println();
        System.out.println(": \t\t  Pozwala na przemieszczanie się między dyskami.");
        System.out.println("\t\t  Przed \":\" należy umieścić literę reprezentującą dysk.");
        System.out.println("CD \t\t  Wyświetla bądź modyfikuje aktualną ścieżkę.");
        System.out.println("CD.. \t  Przechodzi do porzedniego katalogu.");
        System.out.println("CHDIR \t  Wyświetla bądź modyfikuje aktualną ścieżkę.");
        System.out.println("CHDIR..   Przechodzi do porzedniego katalogu.");
        System.out.println("COPY \t  Kopiuje określony plik do podanej ścieżi.");
        System.out.println("DATE \t  Wyświetla aktualną datę.");
        System.out.println("DEL  \t  Usuwa podany plik.");
        System.out.println("DIR \t  Wyświetla zawartość aktualnej ścieżki.");
        System.out.println("EXIT \t  Kończy działanie wiersza poleceń.");
        System.out.println("HELP \t  Wyświetla listę dostępnych komend.");
        System.out.println("MD \t\t  Tworzy nowy katalog.");
        System.out.println("MKDIR \t  Tworzy nowy katalog.");
        System.out.println("MOVE \t  Przenosi określony plik do podanej ścieżi.");
        System.out.println("REN \t  Zmienia nazwę pliku.");
        System.out.println("RENAME \t  Zmienia nazwę pliku.");
        System.out.println("RMDIR \t  Usuwa podany plik lub katalog.");
        System.out.println("SHUTDOWN  Wyłącza komputer.");
        System.out.println("TIME \t  Wyświetla aktualny czas.");
        System.out.println("TREE \t  Wyświetla graficzną reprezentację aktualnej ścieżki.");
        System.out.println("TYPE \t  Wyświetla zawartość podanego pliku tekstowegp.");
        System.out.println("VER \t  Wyświetla wersje systemu operacyjnego.");
        System.out.println();
    }

    public static String CD(String input, String Path)
    {
        String tmp = input.substring(3);
        Path = zmianaSciezki(tmp, Path);
        return Path;
    }

    public static String CHDIR(String input, String Path)
    {
        String tmp = input.substring(6);
        Path = zmianaSciezki(tmp, Path);
        return Path;
    }

    public static String zmianaSciezki(String tmp, String Path)
    {
        String PathPoczatkowa = Path;
        StringBuilder sciezka = new StringBuilder();
        String bs = "\\";

        for (int i = 0; i < tmp.length(); i++)
        {
            String czyBS = "";
            czyBS += tmp.charAt(i);

            if (!czyBS.equals("\\"))
                sciezka.append(tmp.charAt(i));
        }

        sciezka = new StringBuilder(bs.concat(sciezka.toString()));
        Path = Path.concat(sciezka.toString());

        File katalog = new File(Path);

        if(!katalog.exists())
        {
            System.out.println("Podana ścieżka nie istnieje.");
            Path = PathPoczatkowa;
        }
        return Path;
    }

    public static String CDB(String Path)
    {
        if(!Path.equalsIgnoreCase("C:"))
        {
            StringBuilder sb = new StringBuilder(Path);
            sb.reverse();
            Path = Path.substring(0, Path.length()-sb.indexOf("\\")-1);
        }
        return Path;
    }

    public static void MKDIR(String komenda, String Path)
    {
        String folder = komenda.substring(6);
        File file = new File(Path+"\\"+folder);
        file.mkdir();
    }

    public static void MD(String komenda, String Path)
    {
        String folder = komenda.substring(3);
        File file = new File(Path+"\\"+folder);
        file.mkdir();
    }

    public static void DIR(String Path)
    {
        File katalog = new File(Path);
        String zawartosc [] = katalog.list();
        int dirs = 0;
        int files = 0;
        long dirsSize = 0;
        long filesSize = 0;

        System.out.println(" Data modyfikacji\t\t Typ\t\tRozmiar\t\tNazwa");
        System.out.println("---------------------------------------------------------");
        for(int i =0 ; i < zawartosc.length; i++)
        {
            File plik = new File(Path+"\\"+zawartosc[i]);
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            System.out.print(sdf.format(plik.lastModified())+"\t");

            if (plik.isFile())
            {
                System.out.print("\t\t\t");
                files++;
                filesSize += plik.length();
                if (plik.length()<=999)
                    System.out.print("\t"+plik.length()+"\t\t");
                else if (plik.length()>999 && plik.length()<9999999)
                    System.out.print("\t"+plik.length()+"\t");
                else
                    System.out.print("\t"+plik.length());
                System.out.print("\t"+zawartosc[i]);
                System.out.println();
            }
            else
            {
                System.out.print("\t<DIR>\t");
                dirs++;
                System.out.print("\t\t\t");
                System.out.print("\t"+zawartosc[i]);
                System.out.println();
            }
        }
        System.out.println("\t\t\t\t\tPliki: "+files+"\t\t"+filesSize+" Bitów");
        System.out.println("\t\t\t\t\tFoldery: "+dirs+"\t\t");
    }

    public static void DEL(String komenda, String Path)
    {
        String plik = komenda.substring(4);

        if(plik.length()>=1)
        {
            File file = new File(Path + "\\" + plik);
            if (file.exists())
            {
                if (!file.isDirectory())
                    file.delete();
            }
            else
                System.out.println("Podany plik nie istnieje.");
        }
        else
            System.out.println("Wprowadzona komenda jest niepoprawna.");
    }

    public static void RMDIR(String komenda, String Path)
    {
        String plik = komenda.substring(6);

        if(plik.length()>=1)
        {
            File file = new File(Path+"\\"+plik);
            if (file.exists())
                file.delete();
            else
                System.out.println("Podany plik nie istnieje.");
        }
        else
            System.out.println("Wprowadzona komenda jest niepoprawna.");
    }

    public static boolean RENAME(String komenda, String Path)
    {
        boolean nazwaZmieniona = false;
        komenda = komenda.substring(7);
        String [] nazwy = komenda.split(" ");
        File plik = new File(Path+"\\"+nazwy[0]);
        if(plik.exists())
            nazwaZmieniona = plik.renameTo(new File(Path+"\\"+nazwy[1]));
        else
            System.out.println("Podany plik nie istnieje.");

        return nazwaZmieniona;
    }

    public static boolean REN(String komenda, String Path)
    {
        boolean nazwaZmieniona = false;
        komenda = komenda.substring(4);
        String [] nazwy = komenda.split(" ");
        File plik = new File(Path+"\\"+nazwy[0]);
        if(plik.exists())
            nazwaZmieniona = plik.renameTo(new File(Path+"\\"+nazwy[1]));
        else
            System.out.println("Podany plik nie istnieje.");

        return nazwaZmieniona;
    }

    public static void TREE(String Path, String startPath)
    {
        File katalog = new File(Path);
        String [] foldery = katalog.list();

        int sciezka = 0;
        int poczatkowaSciezka = 0;

        for(int i = 0; i < Path.length(); i++)
            if (Path.charAt(i)=='\\')
                sciezka++;

        for(int i = 0; i < startPath.length(); i++)
            if (startPath.charAt(i)=='\\')
                poczatkowaSciezka++;

        int tabulatory = sciezka - poczatkowaSciezka;

        if (foldery != null)
        {
            for (int i=0; i<foldery.length; i++)
            {
                File file = new File(Path+"\\"+foldery[i]);
                if (file.isDirectory())
                {
                    if (i == foldery.length-1)
                    {
                        for (int j = 0; j < tabulatory; j++)
                        {
                            if(j != foldery.length-1)
                                System.out.print("│\t");
                            else
                                System.out.print("\t");
                        }

                        System.out.println("└──"+foldery[i]);
                        TREE(Path + "\\" + foldery[i], startPath);
                    }
                    else
                    {
                        for (int j = 0; j < tabulatory; j++)
                        {
                            if(j != foldery.length-1)
                                System.out.print("│\t");
                            else
                                System.out.print("\t");
                        }
                        System.out.println("├──" + foldery[i]);
                        TREE(Path + "\\" + foldery[i], startPath);
                    }
                }
            }
        }
    }

    public static void TIME()
    {
        DateFormat data = new SimpleDateFormat("HH:mm.ss");
        Calendar aktualnie = Calendar.getInstance();
        System.out.println(data.format(aktualnie.getTime()));
    }

    public static void DATE()
    {
        DateFormat data = new SimpleDateFormat("dd/MM yyyy");
        Calendar aktualnie = Calendar.getInstance();
        System.out.println(data.format(aktualnie.getTime())+" r.");
    }

    public  static void TYPE(String komenda, String Path) throws FileNotFoundException
    {
        komenda = komenda.substring(5);
        Path += "\\"+komenda;
        File file = new File(Path);

        if(file.isFile() && file.exists())
        {
            Scanner scanner = new Scanner(new File(Path));
            while (scanner.hasNextLine())
                System.out.println("\t"+scanner.nextLine());
            System.out.println();
            System.out.println();
            scanner.close();
        }
        else
            System.out.println("Podany plik nie istnieje.");
    }

    public static void COPY(String plik, String Path) throws IOException
    {
        plik = plik.substring(5);
        int dwukropek = plik.indexOf(":");
        int ostatniaSpacja = plik.lastIndexOf(" ",dwukropek);
        String newPath = plik.substring(ostatniaSpacja+1);
        plik = plik.substring(0,ostatniaSpacja);

        File doSkopiowania = new File(Path + "\\" + plik);
        File miejsceDocelowe = new File(newPath);

        if (doSkopiowania.exists())
            if (miejsceDocelowe.isDirectory())
            {
                try
                {
                    File plikDocelowy = new File(newPath+"\\"+plik);
                    Files.copy(doSkopiowania.toPath(), plikDocelowy.toPath());
                }
                catch (java.nio.file.FileAlreadyExistsException e)
                {
                    System.out.println("W podanej lokalizacji już istnieje plik o takiej samej nazwie.");
                }
            }
            else
                System.out.println("Podana ścieżka jest błędna.");
        else
            System.out.println("Podany plik nie istnieje");
    }

    public static void MOVE(String plik, String Path) throws IOException
    {
        plik = plik.substring(5);
        int dwukropek = plik.indexOf(":");
        int ostatniaSpacja = plik.lastIndexOf(" ",dwukropek);
        String newPath = plik.substring(ostatniaSpacja+1);
        plik = plik.substring(0,ostatniaSpacja);

        File doSkopiowania = new File(Path + "\\" + plik);
        File miejsceDocelowe = new File(newPath);

        if (doSkopiowania.exists())
            if (miejsceDocelowe.isDirectory())
            {
                try
                {
                    File plikDocelowy = new File(newPath+"\\"+plik);
                    Files.copy(doSkopiowania.toPath(), plikDocelowy.toPath());
                    doSkopiowania.delete();
                }
                catch (java.nio.file.FileAlreadyExistsException e)
                {
                    System.out.println("W podanej lokalizacji już istnieje plik o takiej samej nazwie.");
                }
            }
            else
                System.out.println("Podana ścieżka jest błędna.");
        else
            System.out.println("Podany plik nie istnieje.");
    }

    public static String zmianaDysku (String input, String Path)
    {
        if(input.equalsIgnoreCase("c:"))
            Path = System.getProperty("user.home");
        else
        {
            File dysk = new File(input);

            if (dysk.exists())
                Path = input.toUpperCase();
            else
                System.out.println("Nie można odnaleźć dysku.");
        }
        return Path;
    }

    public static void VER()
    {
        System.out.println(System.getProperty("os.name")+" [Wersja: "
                +System.getProperty("os.version")+" "
                +System.getProperty("os.arch")+"]");
    }

    public static void SHUTDOWN() throws RuntimeException, IOException
    {
        Runtime.getRuntime().exec("shutdown.exe -s -t 0");
        System.exit(0);
    }
}