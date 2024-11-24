import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class WorkoutPage extends JFrame implements ActionListener {

    private static Workout workout = new Workout();

    Conn conn1 = new Conn(), conn2 = new Conn();

    JButton back, save;

    WorkoutPage(int workoutNumber, String workoutName) {

        getWorkout(workoutNumber, workoutName);

        System.out.println(workout.toString());
        workout.showExercise();

        setTitle("Registro de Exercício");

        setLayout(null);

        back = new JButton("Voltar");
        back.setFont(new Font("Osward", Font.PLAIN, 15));
        back.setBounds(5, 10, 70, 30);
        back.setBorder(new LineBorder(Color.GRAY, 1));
        back.addActionListener(this);
        add(back);

        JLabel title = new JLabel(workout.getWorkoutName());
        title.setFont(new Font("Osward", Font.BOLD, 40));
        title.setBounds(200, 10, 400, 60);
        title.setBorder(new LineBorder(Color.BLACK, 2));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.CENTER);
        add(title);

        save = new JButton("Salvar Treino");
        save.setFont(new Font("Osward", Font.BOLD, 15));
        save.setBounds(630, 25, 150, 30);
        save.setBorder(new LineBorder(Color.GRAY, 1));
        save.addActionListener(this);
        add(save);

        JPanel scrollContent = getJPanelWorkout();

        JScrollPane scrollPane = new JScrollPane(scrollContent);
        scrollPane.setBounds(0, 90, 800, 500);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(16);

        add(scrollPane);

        setSize(815, 600);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        if(ae.getSource() == back) {

            setVisible(false);
            new Home().setVisible(true);

        }
    }

    private void getWorkout(int workoutNumber, String workoutName) {

        workout = new Workout();

        workout.setWorkoutId(workoutNumber);
        workout.setWorkoutName(workoutName);

        String query = "SELECT exercise_id, exercise_name FROM exercise WHERE workout_id = " + workoutNumber;

        try {

            ResultSet rs = conn1.s.executeQuery(query);

            while (rs.next()) {

                int exerciseId = rs.getInt("exercise_id");
                String exerciseName = rs.getString("exercise_name");

                Exercise exercise = new Exercise(exerciseName);

                String setsQuery = "SELECT sets_number, sets_reps, sets_weight FROM sets WHERE exercise_id = " + exerciseId;

                ResultSet rsSets = conn2.s.executeQuery(setsQuery);

                while (rsSets.next()) {

                    int order = rsSets.getInt("sets_number");
                    int reps = rsSets.getInt("sets_reps");
                    int weight = rsSets.getInt("sets_weight");

                    exercise.addSet(new Set(order, reps, weight));
                }

                workout.addExercise(exercise);
            }

        } catch (Exception e) {

            System.out.println(e);
        }
    }

    public static JPanel getJPanelWorkout() {

        JPanel scrollContent = new JPanel();
        scrollContent.setLayout(null);

        int yPosition = 10, exerciseCounter = 1;

        for (Exercise exercise : workout.getExercisesList()) {

            JLabel exerciseLabel = new JLabel(exerciseCounter++ + "º Exercício: " + exercise.getExerciseName());
            exerciseLabel.setFont(new Font("Arial", Font.BOLD, 20));
            exerciseLabel.setBounds(20, yPosition, 760, 30);
            scrollContent.add(exerciseLabel);
            yPosition += 40;

            String[] columnNames = { "Set", "Repetições", "Carga (kg)" };
            String[][] data = new String[exercise.getSetsList().size()][3];

            for (int i = 0; i < exercise.getSetsList().size(); i++) {
                Set set = exercise.getSetsList().get(i);
                data[i][0] = String.valueOf(set.getOrder());
                data[i][1] = String.valueOf(set.getReps());
                data[i][2] = String.valueOf(set.getWeight());
            }

            JTable table = new JTable(data, columnNames);
            table.setBounds(40, yPosition, 700, 20 + 20 * data.length);
            table.setRowHeight(20);
            // table.setEnabled(false);

            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);

            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }

            JScrollPane tableScrollPane = new JScrollPane(table);
            tableScrollPane.setBounds(40, yPosition, 700, 20 + 20 * data.length);
            scrollContent.add(tableScrollPane);

            yPosition += tableScrollPane.getHeight() + 20;
        }

        scrollContent.setPreferredSize(new Dimension(800, yPosition));

        scrollContent.revalidate();
        scrollContent.repaint();

        return scrollContent;
    }
}