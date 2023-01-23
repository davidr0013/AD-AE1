import java.util.Scanner;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Concesionario {
    private static ArrayList<Coche> coches = new ArrayList<Coche>();

    public static void main(String[] args) throws IOException {
        File archivo = new File("coches.dat");
        if (archivo.exists()) {
            try {
                FileInputStream fis = new FileInputStream(archivo);
                ObjectInputStream ois = new ObjectInputStream(fis);

                coches = (ArrayList<Coche>) ois.readObject();
                ois.close();
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Scanner sc = new Scanner(System.in);
        boolean salir = false;
        while (!salir) {
            System.out.println("Menú de opciones:");
            System.out.println("1. Añadir nuevo coche");
            System.out.println("2. Borrar coche por id");
            System.out.println("3. Consultar coche por id");
            System.out.println("4. Listado de coches");
            System.out.println("5. Terminar el programa");
            System.out.println("6. Exportar coches a archivo CSV");
            System.out.print("Seleccione una opción: ");
            int opcion = sc.nextInt();
            sc.nextLine(); // Para consumir el salto de línea
            switch (opcion) {
            case 1:
            	System.out.print("Ingrese la matricula del coche: ");
                String matricula = sc.nextLine();
                boolean matriculaDuplicada = false;
                for (Coche coche : coches) {
                    if (coche.getMatricula().equalsIgnoreCase(matricula)) {
                        matriculaDuplicada = true;
                        break;
                    }
                }
                if (matriculaDuplicada) {
                    System.out.println("La matricula ingresada ya existe");
                    break;
                }
                System.out.print("Ingrese la marca del coche: ");
                String marca = sc.nextLine();
                System.out.print("Ingrese el modelo del coche: ");
                String modelo = sc.nextLine();
                System.out.print("Ingrese el color del coche: ");
                String color = sc.nextLine();
                int id = coches.size() + 1;
                coches.add(new Coche(id, matricula, marca, modelo, color));
                System.out.println("Coche agregado exitosamente, su id = " + id);
                break;
                
            case 2:
                System.out.print("Ingrese el id del coche a borrar: ");
                id = sc.nextInt();
                boolean cocheEncontrado = false;
                for (Coche coche : coches) {
                    if (coche.getId() == id) {
                        coches.remove(coche);
                        System.out.println("Coche borrado exitosamente");
                        cocheEncontrado = true;
                        break;
                    }
                }
                if (!cocheEncontrado) {
                    System.out.println("No se encontró el coche con el id especificado");
                }
                break;
            case 3:
                System.out.print("Ingrese el id del coche a consultar: ");
                id = sc.nextInt();
                cocheEncontrado = false;
                for (Coche coche : coches) {
                    if (coche.getId() == id) {
                        System.out.println("Matricula: " + coche.getMatricula());
                        System.out.println("Marca: " + coche.getMarca());
                        System.out.println("Modelo: " + coche.getModelo());
                        System.out.println("Color: " + coche.getColor());
                        cocheEncontrado = true;
                        break;
                    }
                }
                if (!cocheEncontrado) {
                    System.out.println("No se encontró el coche con el id especificado");
                }
                break;
            case 4:
                if(coches.isEmpty()){
                    System.out.println("No hay coches registrados");
                }else{
                    for (Coche coche : coches) {
                        System.out.println("Id: " + coche.getId());
                        System.out.println("Matricula: " + coche.getMatricula());
                        System.out.println("Marca: " + coche.getMarca());
                        System.out.println("Modelo: " + coche.getModelo());
                        System.out.println("Color: " + coche.getColor());
                    }
                }
                break;
            case 5:
                salir = true;
                try {
                    FileOutputStream fos = new FileOutputStream("coches.dat");
                    ObjectOutputStream oos = new ObjectOutputStream(fos);

                    oos.writeObject(coches);
                    oos.close();
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("Programa finalizado.");
                break;
            case 6:
                FileWriter fw = new FileWriter("coches.csv");
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("id;matricula;marca;modelo;color");
                bw.newLine();
                for (Coche coche : coches) {
                    bw.write(coche.getId() + ";" + coche.getMatricula() + ";" + coche.getMarca() + ";" + coche.getModelo() + ";" + coche.getColor());
                    bw.newLine();
                }
                bw.close();
                fw.close();
                System.out.println("Coches exportados a coches.csv");
                break;


                default:
                    System.out.println("Opción inválida, por favor seleccione una opción válida.");
            }
        }
        sc.close();
    }
}

