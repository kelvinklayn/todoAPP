package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Project;
import util.ConnectionFactory;

/**
 *
 * @author kelvi
 */
public class ProjectController {
    
    public void save (Project project){
        
        String sql = "INSERT INTO projects ( name,"
                + " description, "
                + " createdAt, "
                + " updatedAt) VALUES (?,?,?,?)";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            statement.execute();
            
            
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao salvar o projeto " + ex.getMessage(),ex );
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
    
    public void update (Project project){

        String sql = "UPDATE projects SET "
                + "name = ?,"
                + "description = ?,"
                + "createdAt = ?,"
                + "updatedAt = ?,"
                + "Where id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            statement.setInt(5, project.getId());
            statement.execute();

        } catch (Exception ex) {
            throw new RuntimeException("Erro ao atualizar o projeto " + ex.getMessage(),ex );
            
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }

    public void removeById(int projectId){
        String sql = "DELETE FROM projects WHERE id = '?'";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, projectId);
            statement.execute();

        } catch (Exception ex) {
            throw new RuntimeException("Erro ao deletar o projeto" + ex.getMessage(),ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }

     public List<Project> getAll(){
        String sql ="SELECT * FROM projects";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        List<Project> projects = new ArrayList<Project>();
        
         try {
            /*Criando a conexão*/
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            
            /*Valor retornado pela execução da query*/
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                Project project = new Project(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getDate("createdAt"),
                        resultSet.getDate("updatedAt")
                );
                projects.add(project);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao buscar os projetos " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }

        return projects;
     }
    
}
