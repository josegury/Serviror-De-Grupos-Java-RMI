/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package centralizedgroups;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

/**
 *
 * @author usuario
 */
public class Client extends UnicastRemoteObject implements ClientInterface{

    public Client() throws RemoteException{
        super();
        
    }
    
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException{
        
        Scanner sc = new Scanner(System.in);
       
         String alias;
         String hostname;
         String aliasgrupo;
         String aliascliente;
         int num1,num2;
         
        System.setProperty("java.security.policy", "C:/Users/usuario/Desktop/nbworkspace/CentralizedGroups/server.policy");
        
        if (System.getSecurityManager() == null){
            System.setSecurityManager(new SecurityManager());
        }
        
        Client client = new Client();
        System.out.printf("Inserte alias de cliente: ");
        aliascliente=sc.nextLine();      
        GroupServerInterface stub;
        
            stub = (GroupServerInterface) Naming.lookup("rmi://localhost:1099/HolaServer");
        
        
         
         do{
         System.out.println("Elija opción:");
         System.out.println("1. Crear grupo");
         System.out.println("2. Eliminar grupo");
         System.out.println("3. Añadir miembro al grupo");
         System.out.println("4. Eliminar miembro del grupo");
         System.out.println("5. Bloquear altas/bajas");
         System.out.println("6. Desbloquear altas/bajas");
         System.out.println("7. Número de grupos en servidor");
         System.out.println("8. Miembros en grupo a indicar");
         System.out.println("9. Terminar ejecución");
         
         num1=sc.nextInt();
          switch (num1) {
              
            case 1:  
                System.out.println("\t_______________CREAR GRUPO_______________");
                System.out.printf("Inserte alias de grupo: ");
                aliasgrupo=sc.next();                              
                        System.out.printf("Inserte hostname de propietario: ");
                        hostname=sc.next();
                    System.out.println(stub.createGroup(aliasgrupo, aliascliente, hostname));
                    System.out.println("Grupo creado");
                    System.out.println("\t---------------------------------------------------");
                     break;
                
            case 2: System.out.println("\t_______________ELIMINACION DE GRUPO_______________");
                System.out.print("\nInserte alias de grupo: ");
                        aliasgrupo=sc.next();
                        System.out.print("\nInserte alias de propietario: ");
                        alias=sc.next(); 
                    if(!(stub.removeGroup(aliasgrupo, alias))){
                        System.out.println("No se pudo borrar el grupo seleccionado");
                    };
                    System.out.println("\t---------------------------------------------------");
                     break;
                
            case 3: System.out.println("\t_______________NUEVO MIEMBRO_______________");
                System.out.print("\nInserte alias de miembro nuevo: ");
                        alias=sc.next();
                        System.out.print("\nInserte hostname: ");
                        hostname=sc.next(); 
                        System.out.print("\nInserte id de grupo: ");
                        num2=sc.nextInt();
                    if(stub.addMember(num2, alias, hostname) ==null){
                        System.out.println("No se pudo añadir miembro");
                    }
                    else{
                         System.out.println("Miembro insertado");
                    }
                    System.out.println("\t---------------------------------------------------");
                     break;
                
            case 4: System.out.println("\t_______________BORRAR MIEMBRO_______________");
                System.out.print("\nInserte alias de miembro nuevo: ");
                        alias=sc.next();
                        System.out.print("\nInserte hostname: ");
                        hostname=sc.next(); 
                        System.out.print("\nInserte id de grupo: ");
                        num2=sc.nextInt();
                    if(!(stub.removeMember(num2, alias))){
                        System.out.println("No se pudo eliminar miembro");
                    }
                    else{
                        System.out.println("Miembro eliminado");
                    };
                    System.out.println("\t---------------------------------------------------");
                     break;
                
            case 5:  System.out.println("\t_______________BLOQUEO DE ALTAS/BAJAS_______________");
                System.out.print("\nInserte id de grupo: ");
                num2=sc.nextInt();
                    if(stub.StopMembers(num2)){
                        System.out.println("Altas/bajas bloqueadas en "+num2);
                    }
                    else{
                        System.out.println("No se pudieron bloquear altas/bajas en "+num2);
                    }
                    System.out.println("\t---------------------------------------------------");
                     break;
                
            case 6:  System.out.println("\t_______________DESBLOQUEO DE ALTAS/BAJAS_______________");
                System.out.print("\nInserte id de grupo: ");
                num2=sc.nextInt();
                    if(stub.AllowMembers(num2)){
                        System.out.println("Altas/bajas bloqueadas en "+num2);
                    }
                    else{
                        System.out.println("No se pudieron bloquear altas/bajas en "+num2);
                    }
                    System.out.println("\t---------------------------------------------------");
                     break;
                
           case 7: System.out.println("\t_______________NUMERO DE GRUPOS EN SERVER: ");
                System.out.print(stub.numgrup());
                System.out.println("\t---------------------------------------------------");
                break;
                    
            case 8: System.out.println("\t_______________MIEMBROS DEL GRUPO:");
                System.out.print("\nInserte id de grupo: ");
                num2=sc.nextInt(); 
                System.out.println(stub.MemberList(num2));
                System.out.println("\t---------------------------------------------------");
                     break;
                
            case 9:  System.out.println("\t---------------------------------------------------");
                client.unexportObject(client, true);
                     break;
                        
    }
         }while(num1!=9);
         
    }
        
}

