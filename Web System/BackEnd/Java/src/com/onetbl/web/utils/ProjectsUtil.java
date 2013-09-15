package com.onetbl.web.utils;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import com.onetbl.db.dao.ProjectsManagement;
import com.onetbl.db.exceptions.DataBaseException;
import com.onetbl.db.exceptions.ProjectNotFoundException;
import com.onetbl.web.beans.Project;
import com.onetbl.web.exceptions.InvalidInputException;

public class ProjectsUtil {

	public static Project getProject(int ownerID, int projectID) {
		try {
			return ProjectsManagement.getProjectDetails(ownerID, projectID);
		} catch (DataBaseException e) {
			return null ;
		}
	}


	public static boolean updateProject(int ownerId, Project project) {
		try {
			ProjectsManagement.updateProject(project.getId(), project.getName(), project.getDescription()
					, project.getIconUrl());
			return true;
		} catch (DataBaseException e) {
			return false;
		}
		}

	public static Project addProject(Project p) {
		try {
			p.setToken(generateToken());
			ProjectsManagement.addNewProject(p);
			return ProjectsManagement.getLastProject(p.getOwnerId());
		} catch (DataBaseException e) {
			return null;
		}
	}
	
	private static String generateToken(){
	    UUID token = UUID.randomUUID();
	    return token.toString();
		
	}
	public static boolean deleteProject(Project p) {
		try {
			ProjectsManagement.removeProject(p.getId(), p.getOwnerId());
			return true;
		} catch (DataBaseException | ProjectNotFoundException e) {
			return false;
		}
	}

	public static ArrayList<Project> getUserProjects(int userId) {
		try {
			return ProjectsManagement.getUserProjects(userId);
		} catch (DataBaseException e) {
			return null;
		}
	}
	
	public static void main(String[] args) 
	{
		//System.out.println(generateToken());
	}
}
