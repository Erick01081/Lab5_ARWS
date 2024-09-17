package edu.eci.arsw.blueprints.main;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;

@SpringBootApplication(scanBasePackages = {"edu.eci.arsw.blueprints"})
public class BlueprintsApp implements CommandLineRunner {

    @Autowired
    BlueprintsServices bbpServices;

    Scanner scanner = new Scanner(System.in);

    public static void main(String arg[]) {
        SpringApplication.run(BlueprintsApp.class, arg);
    }

    @Override
    public void run(String... args) throws BlueprintNotFoundException{
        boolean running = true;

        Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15)};
        Blueprint bp0 = new Blueprint("oscar", "prueba", pts0);
        bbpServices.addNewBlueprint(bp0);


        System.out.println(bp0);

        while(running){
            System.out.println("=====Aplicación de Gestor de Planos=====");
            System.out.println("");
            System.out.println("Digita una opción:\n1.Buscar plano por autor y nombre\n2.Consultar planos de un autor\n3.Registrar un nuevo plano\n4.Consultar todos los planos");
            System.out.println("");
            String selection0 = scanner.nextLine();

            if(selection0.equals("1")){
                System.out.println("Digite el nombre del autor");
                String name = scanner.nextLine();
                System.out.println("Digite el nombre del plano");
                String author = scanner.nextLine();
                System.out.println(bbpServices.getBlueprint(name, author));
            }

            else if(selection0.equals("2")){
                System.out.println("Digite el nombre del autor");
                String name = scanner.nextLine();
                System.out.println(bbpServices.getBlueprintsByAuthor(name));
            }

            else if(selection0.equals("3")){
                System.out.println("Digite el nombre del autor");
                String author = scanner.nextLine();
                System.out.println("Digite el nombre del plano");
                String name = scanner.nextLine();
                System.out.println("Digite el numero de puntos que tendrá el plano");
                String points= scanner.nextLine();
                int numberOfPoints= Integer.parseInt(points);

                bbpServices.addNewBlueprint(new Blueprint(author,name,getPointsFromUser(numberOfPoints)));
            }
            else if(selection0.equals("4")){
                System.out.println("Planos existentes: ");
                System.out.println(bbpServices.getAllBlueprints());
            }
        }
    }

    private Point[] getPointsFromUser(int numberOfPoints) {
        Point[] points = new Point[numberOfPoints];

        for (int i = 0; i < numberOfPoints; i++) {
            System.out.println("Digite las coordenadas del punto " + (i + 1) + " en el formato 'x y':");
            String[] coordinates = scanner.nextLine().split(" ");
            int x = Integer.parseInt(coordinates[0]);
            int y = Integer.parseInt(coordinates[1]);
            points[i] = new Point(x, y);
        }

        return points;
    }
}