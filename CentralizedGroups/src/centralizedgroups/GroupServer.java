/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package centralizedgroups;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author usuario
 */
public class GroupServer extends UnicastRemoteObject implements GroupServerInterface{
    LinkedList<ObjectGroup> listaGrupos;
    int idGrupos;
    
    public GroupServer() throws RemoteException{
        super();
        listaGrupos=new LinkedList();
        idGrupos=-1;
    
    }
    
    
    public static void main(String[] args) throws MalformedURLException {
        
        try {
            GroupServer Server;
            
            System.setProperty("java.security.policy", "C:/Users/usuario/Desktop/nbworkspace/CentralizedGroups/server.policy");
            System.setSecurityManager(new SecurityManager());
            int numPuerto=1099;
            LocateRegistry.createRegistry(numPuerto);
            Server=new GroupServer();
                
            try {
                Naming.rebind("//localhost"+":"+numPuerto+"/HolaServer", Server);
            } catch (MalformedURLException ex) {
                Logger.getLogger(GroupServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (RemoteException ex) {
            Logger.getLogger(GroupServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("\t____________________SERVIDOR LANZADO____________________");        
        
    }
    
    @Override
    public int numgrup() {
        return this.listaGrupos.size();
    }
    
    @Override
    public int createGroup(String galias, String oalias, String ohostname) {
        if(findGroup(galias)==-1){
            idGrupos=idGrupos+1;
            listaGrupos.add(new ObjectGroup(galias, idGrupos, oalias, ohostname));
            System.out.println("Grupo creado, ahora hay "+listaGrupos.size());
            return idGrupos;
        }
        else{
            return -1;
        }
    }

        @Override
    public boolean removeGroup(String galias, String oalias) {
        for(int i=0; i<this.listaGrupos.size();i++){
            if(listaGrupos.get(i).groupName.equals(galias) && listaGrupos.get(i).propietario.alias.equals(oalias)){
                this.listaGrupos.remove(i);
                return true;
            }
        }
        return false;
    }
        
    @Override
    public int findGroup(String galias) {
        for(int i=0; i<this.listaGrupos.size();i++){
            if(listaGrupos.get(i).groupName.equals(galias)){
                return listaGrupos.get(i).idGroup;
            }
        }
        return -1;
    }

    @Override
    public GroupMember addMember(int gid, String alias, String hostname) {
        for(int i=0; i<this.listaGrupos.size();i++){
            if(listaGrupos.get(i).idGroup==gid){
                if(listaGrupos.get(i).isMember(alias)==null){
                    try {
                        return listaGrupos.get(i).addMember(alias, hostname);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GroupServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else{
                    return null;
                }
            }
        }
        return null;
    }
    
    public boolean removeMember(int gid, String alias) {
        for(int i=0; i<this.listaGrupos.size();i++){
            if(listaGrupos.get(i).idGroup==gid){
                if(listaGrupos.get(i).isMember(alias)!=null){
                    try {
                        listaGrupos.get(i).removeMember(alias);
                        return true;
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GroupServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else{
                    return false;
                }
            }
        }
        return false;
    }
    
         public String MemberList(int grupid) {
         String solve="";
         
         for(int j=0;j<this.listaGrupos.get(grupid).members.size();j++){
            solve+="Alias: "+this.listaGrupos.get(grupid).members.get(j).alias+" Hostname: "+this.listaGrupos.get(grupid).members.get(j).hostname+"\n";
         }
         
        return solve;
    }

    @Override
    public GroupMember isMember(int gid, String alias) {
         for(int i=0; i<this.listaGrupos.size();i++){
              if(listaGrupos.get(i).idGroup==gid){
                  return listaGrupos.get(i).isMember(alias);
              }
         }
         return null;
    }

    
    
    @Override
    public boolean StopMembers(int gid) {
        for(int i=0; i<this.listaGrupos.size();i++){
              if(listaGrupos.get(i).idGroup==gid){
                  listaGrupos.get(i).StopMembers();
                  return true;
              }
         }
        return false;
    }

    @Override
    public boolean AllowMembers(int gid) {
        for(int i=0; i<this.listaGrupos.size();i++){
              if(listaGrupos.get(i).idGroup==gid){
                  listaGrupos.get(i).AllowMembers();
                  return true;
              }
         }
        return false;
    }
}
