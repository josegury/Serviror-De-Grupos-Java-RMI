/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package centralizedgroups;

import java.io.*;

/**
 *
 * @author usuario
 */
public class GroupMember implements Serializable{
    
    String alias;
    String hostname;
    int idMember;
    int idGroup;
    
    public GroupMember(String al, String host, int idMem, int idGr){
        alias = al;
        hostname = host;
        idMember = idMem;
        idGroup = idGr;
    }
    
}
