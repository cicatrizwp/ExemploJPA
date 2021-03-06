/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUtil;
import javax.persistence.TypedQuery;
import model.Professor;

/**
 *
 * @author cicatriz
 */
public class ProfessorDAO {
    
    private static ProfessorDAO instance = new ProfessorDAO();
    
    public static ProfessorDAO getInstance() {
        return instance;
    }
    
    public void salvar(Professor professor) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            if (professor.getId() != null) {
                em.merge(professor);
            } else {
                em.persist(professor);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RunTimeException(e);
        } finally {
            PersistenceUtil.close(em);
        }
    }
    
    public void excluir(Professor professor) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.getReference(Professor.class, professor.getId()));
            tx.commit();
        } catch (Exception e) {
            if (tx != null & tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            PersistenceUtil.close(em);
        }
    }
    
    public Professor getProfessor(long id) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        Professor professor = null;
        try {
            tx.begin();
            professor = em.find(Professor.class, id);
            tx.commit();
        } catch (Exception e) {
            if(tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            PersistenceUtil.close(em);
        }
        return professor;
    }
    
    public List<Professor> getAllProfessores() {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Professor> professores = null;
        try {
            tx.begin();
            TypedQuery<Professor> query = 
                    em.createQuery("SELECT p FROM Professor p", Professor.class);
            professores = query.getResultList();
            tx.commit();
        } catch (Exception e) {
            if(tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            PersistenceUtil.close(em);
        }
        return professores;
    }
    
}
