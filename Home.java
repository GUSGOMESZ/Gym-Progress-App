import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

public class Home extends JFrame implements ActionListener {

    private static int loader = 0;
    private static final Conn conn = new Conn();
    private static final ArrayList<String> workoutNames = new ArrayList<>();

    JButton workout1, workout2, workout3, workout4, workout5, createWorkout;

    Home() {

        setTitle("HOME");

        setLayout(null);

        getWorkoutName();

        JLabel title = new JLabel("Gym Tracker");
        title.setFont(new Font("Osward", Font.BOLD, 40));
        title.setBounds(200, 10, 400, 60);
        title.setBorder(new LineBorder(Color.BLACK, 2));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.CENTER);
        add(title);

        workout1 = new JButton(workoutNames.size() == loader ? "Treino 1" : workoutNames.get(loader++));
        workout1.setFont(new Font("Osward", Font.BOLD, 35));
        workout1.setBounds(200, 100, 400, 60);
        workout1.setBorder(new LineBorder(Color.GRAY, 1));
        workout1.addActionListener(this);
        add(workout1);

        workout2 = new JButton(workoutNames.size() == loader ? "Treino 2" : workoutNames.get(loader++));
        workout2.setFont(new Font("Osward", Font.BOLD, 35));
        workout2.setBounds(200, 180, 400, 60);
        workout2.setBorder(new LineBorder(Color.GRAY, 1));
        workout2.addActionListener(this);
        add(workout2);

        workout3 = new JButton(workoutNames.size() == loader ? "Treino 3" : workoutNames.get(loader++));
        workout3.setFont(new Font("Osward", Font.BOLD, 35));
        workout3.setBounds(200, 260, 400, 60);
        workout3.setBorder(new LineBorder(Color.GRAY, 1));
        workout3.addActionListener(this);
        add(workout3);

        workout4 = new JButton(workoutNames.size() == loader ? "Treino 4" : workoutNames.get(loader++));
        workout4.setFont(new Font("Osward", Font.BOLD, 35));
        workout4.setBounds(200, 340, 400, 60);
        workout4.setBorder(new LineBorder(Color.GRAY, 1));
        workout4.addActionListener(this);
        add(workout4);

        workout5 = new JButton(workoutNames.size() == loader ? "Treino 5" : workoutNames.get(loader++));
        workout5.setFont(new Font("Osward", Font.BOLD, 35));
        workout5.setBounds(200, 420, 400, 60);
        workout5.setBorder(new LineBorder(Color.GRAY, 1));
        workout5.addActionListener(this);
        add(workout5);

        createWorkout = new JButton("Cadastrar Treino");
        createWorkout.setFont(new Font("Osward", Font.BOLD, 25));
        createWorkout.setBounds(200, 500, 400, 60);
        createWorkout.setBorder(new LineBorder(Color.GRAY, 1));
        createWorkout.addActionListener(this);
        add(createWorkout);

        setSize(800, 620);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        int workoutNumber = 0;
        String query = "";

        if(ae.getSource() == workout1) {

            workoutNumber = 1;

        } else if(ae.getSource() == workout2) {

            workoutNumber = 2;

        } else if(ae.getSource() == workout3) {

            workoutNumber = 3;

        } else if(ae.getSource() == workout4) {

            workoutNumber = 4;

        } else if(ae.getSource() == workout5) {

            workoutNumber = 5;

        } else if(ae.getSource() == createWorkout) {

            setVisible(false);
            new Register().setVisible(true);

        }

        query = "SELECT * FROM workout WHERE workout_id = " + workoutNumber;

        if(workoutNumber != 0) {

            String workoutName = "";

            workoutName = updatePage(query);

            if(!(workoutName.isEmpty())) {

                setVisible(false);
                new WorkoutPage(workoutNumber, workoutName).setVisible(true);

            } else {
                JOptionPane.showMessageDialog(null, "Não há exercícios cadastrados para o treino " + workoutNumber + ".");
            }
        }
    }

    public static void main(String[] args) {
        new Home();
    }

    public static void getWorkoutName() {

        String query = "SELECT * FROM workout";

        try {

            ResultSet rs = conn.s.executeQuery(query);

            if (rs.next()) {

                ResultSetMetaData metaData = rs.getMetaData();
                int registeredWorkouts = metaData.getColumnCount();

                do {
                    for (int i = 2; i <= registeredWorkouts; i+=2) {
                        workoutNames.add(rs.getString(i));
                    }
                } while (rs.next());

            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static String updatePage(String query) {

        String workoutName = "";

        try {

            ResultSet rs = conn.s.executeQuery(query);

            if (rs.next()) {

                workoutName = rs.getString("workout_name");
            }

            return workoutName;

        } catch(Exception e) {

            System.out.println(e);

            return workoutName;
        }
    }
}
