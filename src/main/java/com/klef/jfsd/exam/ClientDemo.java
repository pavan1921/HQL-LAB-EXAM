package com.klef.jfsd.exam;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class ClientDemo {
    public static void main(String[] args) {
        insertDepartment("Finance", "Chicago", "Jane Doe");
        insertDepartment("Operations", "Los Angeles", "Robert Brown");

        deleteDepartment(1); // Delete the department with ID 1
    }

    // Method to insert a department
    public static void insertDepartment(String name, String location, String hodName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            Department dept = new Department();
            dept.setName(name);
            dept.setLocation(location);
            dept.setHodName(hodName);

            session.persist(dept);
            transaction.commit();
            System.out.println("Department inserted: " + dept);
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    // Method to delete a department by ID using HQL
    public static void deleteDepartment(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            String hql = "delete from Department where id = :deptId";
            int result = session.createQuery(hql)
                                .setParameter("deptId", id)
                                .executeUpdate();
            transaction.commit();
            System.out.println(result + " department(s) deleted.");
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
