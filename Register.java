import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class Register extends JFrame implements ActionListener {

    private static int exerciseCounter = 0;
    private static final Conn conn = new Conn();
    private static String workoutName, exercisesQuantity;
    private static Workout workout;

    // First PART
    JButton back, next;
    JComboBox<String> trainingBox, exercisesQuantityBox;

    // Second PART
    private JButton nextExercise;
    private JTextField exerciseNameTextField;
    private JComboBox<String> setsQuantityOptionsBox;
    private JPanel inputPanel;
    private static final ArrayList<JTextField> repetitionsTextFields = new ArrayList<>();
    private static final ArrayList<JTextField> weightTextFields = new ArrayList<>();

    // Third PART
    private JPanel contentPanel;
    private JButton finishRegister, backToMenu;

    Register () {

        exerciseCounter = 0;

        setTitle("Registro de Exercício");

        setLayout(null);

        back = new JButton("Voltar");
        back.setFont(new Font("Osward", Font.PLAIN, 15));
        back.setBounds(5, 10, 70, 30);
        back.setBorder(new LineBorder(Color.GRAY, 1));
        back.addActionListener(this);
        add(back);

        JLabel title = new JLabel("Qual treino deseja cadastrar ?");
        title.setFont(new Font("Osward", Font.BOLD, 40));
        title.setBounds(100, 10, 600, 60);
        // title.setBorder(new LineBorder(Color.BLACK, 2));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.CENTER);
        add(title);

        String[] workouts = getWorkoutName();
        trainingBox = new JComboBox<>(workouts);
        trainingBox.setFont(new Font("Osward", Font.PLAIN, 20));
        trainingBox.setBounds(200, 100, 400, 40);
        add(trainingBox);

        String[] exercisesQuantityOptions = {"Selecione a quantidade de exercícios", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};
        exercisesQuantityBox = new JComboBox<>(exercisesQuantityOptions);
        exercisesQuantityBox.setFont(new Font("Osward", Font.PLAIN, 20));
        exercisesQuantityBox.setBounds(200, 160, 400, 40);
        add(exercisesQuantityBox);

        JLabel warning = new JLabel("<html>Tenha em mente que se o treino selecionado já forá cadastrado, as informações anteriores serão excluídas.</html>");
        warning.setFont(new Font("Raleway", Font.BOLD, 15));
        warning.setBounds(200, 220, 400, 60);
        add(warning);

        next = new JButton("Cadastrar Exercícios");
        next.setFont(new Font("Osward", Font.BOLD, 15));
        next.setBounds(200, 300, 400, 30);
        next.setBorder(new LineBorder(Color.GRAY, 1));
        next.addActionListener(this);
        add(next);

        setSize(800, 400);
        setVisible(true);
    }

    Register (String workoutName, int exercises) {

        setTitle("Registro de Exercício");

        setLayout(null);

        JLabel title = new JLabel(workoutName + " - Exercício " + (exerciseCounter + 1));
        title.setFont(new Font("Osward", Font.PLAIN, 40));
        title.setBounds(100, 10, 600, 60);
        title.setBorder(new LineBorder(Color.BLACK, 2));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.CENTER);
        add(title);

        JLabel exerciseNameLabel = new JLabel("Nome: ");
        exerciseNameLabel.setFont(new Font("Osward", Font.PLAIN, 20));
        exerciseNameLabel.setBounds(100, 100, 100, 30);
        add(exerciseNameLabel);

        exerciseNameTextField = new JTextField();
        exerciseNameTextField.setBounds(210, 100, 490, 30);
        exerciseNameTextField.setFont(new Font("Osward", Font.BOLD, 15));
        add(exerciseNameTextField);

        JLabel setsQuantityLabel = new JLabel("Quantidade de séries: ");
        setsQuantityLabel.setFont(new Font("Osward", Font.PLAIN, 20));
        setsQuantityLabel.setBounds(100, 150, 240, 30);
        add(setsQuantityLabel);

        String[] setsQuantityOptions = {"Selecione a quantidade de séries", "1", "2", "3", "4", "5", "6"};
        setsQuantityOptionsBox = new JComboBox<>(setsQuantityOptions);
        setsQuantityOptionsBox.setFont(new Font("Osward", Font.PLAIN, 20));
        setsQuantityOptionsBox.setBounds(320, 150, 380, 30);
        setsQuantityOptionsBox.addActionListener(this);
        add(setsQuantityOptionsBox);

        inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBounds(150, 220, 500, 250);
        inputPanel.setBorder(new LineBorder(Color.GRAY, 1)); // Para visualização
        add(inputPanel);

        nextExercise = new JButton("Próximo");
        nextExercise.setFont(new Font("Osward", Font.BOLD, 15));
        nextExercise.setBounds(100, 500, 600, 30);
        nextExercise.setBorder(new LineBorder(Color.GRAY, 1));
        nextExercise.addActionListener(this);
        add(nextExercise);

        setSize(800, 600);
        setVisible(true);
    }

    public Register(Workout workout) {

        System.out.println(workout.toString());
        workout.showExercise();

        setTitle("Registro de Exercício");

        setLayout(null);

        backToMenu = new JButton("Voltar para o Menu");
        backToMenu.setFont(new Font("Osward", Font.BOLD, 15));
        backToMenu.setBounds(20, 25, 150, 30);
        backToMenu.setBorder(new LineBorder(Color.GRAY, 1));
        backToMenu.addActionListener(this);
        add(backToMenu);

        JLabel title = new JLabel(workout.getWorkoutName());
        title.setFont(new Font("Osward", Font.BOLD, 40));
        title.setBounds(200, 10, 400, 60);
        title.setBorder(new LineBorder(Color.BLACK, 2));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.CENTER);
        add(title);

        finishRegister = new JButton("Cadastrar Treino");
        finishRegister.setFont(new Font("Osward", Font.BOLD, 15));
        finishRegister.setBounds(630, 25, 150, 30);
        finishRegister.setBorder(new LineBorder(Color.GRAY, 1));
        finishRegister.addActionListener(this);
        add(finishRegister);

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

        // First PART
        if(ae.getSource() == next) {

            workoutName = (String) trainingBox.getSelectedItem();
            exercisesQuantity = (String) exercisesQuantityBox.getSelectedItem();

            if (workoutName.equals("Selecionar Treino") || exercisesQuantity.equals("Selecione a quantidade de exercícios")) {

                if(workoutName.equals("Selecionar Treino")) {
                    JOptionPane.showMessageDialog(this, "Por favor, selecione um treino.");
                } else {
                    JOptionPane.showMessageDialog(this, "Por favor, selecione uma quantidade válida de exercícios.");
                }

            } else {

                setVisible(false);

                int workoutID = -1;
                String[] auxWorkoutsName = getWorkoutName();

                for(String singleWorkout : auxWorkoutsName) {

                    workoutID++;

                    if(singleWorkout.equals(workoutName)) break;
                }

                workout = new Workout(workoutID, workoutName);

                new Register(workoutName, Integer.parseInt(exercisesQuantity));

            }

        } else if(ae.getSource() == back) {

            setVisible(false);
            new Home().setVisible(true);

        }

        // Second PART
        if (ae.getSource() == setsQuantityOptionsBox) {

            repetitionsTextFields.clear();
            weightTextFields.clear();

            inputPanel.removeAll();

            inputPanel.setLayout(new GridBagLayout());

            String selected = (String) setsQuantityOptionsBox.getSelectedItem();

            if (!selected.equals("Selecione a quantidade de séries")) {

                int seriesCount = Integer.parseInt(selected);

                JPanel verticalPanel = new JPanel();
                verticalPanel.setLayout(new BoxLayout(verticalPanel, BoxLayout.Y_AXIS));

                for (int i = 1; i <= seriesCount; i++) {

                    verticalPanel.add(getjPanel(i));
                }
                
                inputPanel.add(verticalPanel);
            }
            
            inputPanel.revalidate();
            inputPanel.repaint();
            revalidate();
            repaint();

        } else if (ae.getSource() == nextExercise) {

            String exerciseName = exerciseNameTextField.getText();

            if(exerciseName.isEmpty()) {

                JOptionPane.showMessageDialog(this, "Por favor, dê um nome ao exercício.");

            } else {

                String selected = (String) setsQuantityOptionsBox.getSelectedItem();

                if(selected.equals("Selecione a quantidade de séries")) {

                    JOptionPane.showMessageDialog(this, "Por favor, selecione a quantidade de séries.");

                } else {

                    if(!validateSetDataTextFields()) {

                        JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.");

                    } else {

                        workout.addExercise(getExercise(exerciseName));

                        exerciseCounter++;

                        if(exerciseCounter == Integer.parseInt(exercisesQuantity)) {

                            setVisible(false);
                            new Register(Register.workout).setVisible(true);

                        } else {

                            setVisible(false);
                            new Register(workoutName, Integer.parseInt(exercisesQuantity)).setVisible(true);

                        }
                    }
                }
            }
        }

        // Third PART
        if(ae.getSource() == backToMenu) {

            setVisible(false);
            new Home().setVisible(true);

        } else if(ae.getSource() == finishRegister) {

            try {
                saveWorkout();

            } catch (SQLException e) {

                throw new RuntimeException(e);
            }

            setVisible(false);
            new Home().setVisible(true);
        }
    }

    public static String[] getWorkoutName() {

        ArrayList<String> options = new ArrayList<>();

        String query = "SELECT * FROM workout";

        options.add("Selecionar Treino");

        try {

            ResultSet rs = conn.s.executeQuery(query);

            if (rs.next()) {

                ResultSetMetaData metaData = rs.getMetaData();
                int registeredWorkouts = metaData.getColumnCount();

                do {
                    for (int i = 2; i <= registeredWorkouts; i+=2) {

                        options.add(rs.getString(i));
                    }
                } while (rs.next());
            }

        } catch (Exception e) {

            System.out.println(e);
        }

        for(int i = options.size(); i < 6; i++) {
            options.add("Treino " + i);
        }

        return options.toArray(new String[0]);
    }

    private static Exercise getExercise(String exerciseName) {

        Exercise exercise = new Exercise(exerciseName);

        for(int i = 0; i < repetitionsTextFields.size(); i++) {

            int reps = Integer.parseInt(repetitionsTextFields.get(i).getText());
            int weight = Integer.parseInt(weightTextFields.get(i).getText());

            Set set = new Set((i + 1), reps, weight);

            exercise.addSet(set);
        }

        return exercise;
    }

    private static JPanel getjPanel(int i) {

        JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel seriesLabel = new JLabel(i + "º Série: ");
        seriesLabel.setFont(new Font("Osward", Font.BOLD, 20));
        rowPanel.add(seriesLabel);

        JLabel repetitionLabel = new JLabel(" Repetições:  ");
        repetitionLabel.setFont(new Font("Osward", Font.BOLD, 20));
        rowPanel.add(repetitionLabel);

        JTextField repetitionsField = new JTextField(5);
        repetitionsField.setFont(new Font("Osward", Font.PLAIN, 15));
        repetitionsTextFields.add(repetitionsField);
        rowPanel.add(repetitionsField);

        JLabel weightLabel = new JLabel(" Carga:  ");
        weightLabel.setFont(new Font("Osward", Font.BOLD, 20));
        rowPanel.add(weightLabel);

        JTextField weightField = new JTextField(5);
        weightField.setFont(new Font("Osward", Font.PLAIN, 15));
        weightTextFields.add(weightField);
        rowPanel.add(weightField);

        return rowPanel;
    }

    private static boolean validateSetDataTextFields() {

        for(int i = 0; i < repetitionsTextFields.size(); i++) {

            if(repetitionsTextFields.get(i).getText().isEmpty() || weightTextFields.get(i).getText().isEmpty()) {
                return false;
            }
        }

        return true;
    }

    private static void saveWorkout() throws SQLException {

        String queryDeleteFromSet = "DELETE FROM sets WHERE workout_id = " + workout.getWorkoutId();

        conn.s.executeUpdate(queryDeleteFromSet);

        String queryDeleteFromExercise = "DELETE FROM exercise WHERE workout_id = " + workout.getWorkoutId();

        conn.s.executeUpdate(queryDeleteFromExercise);

        String query = "UPDATE workout SET workout_name = '" + workout.getWorkoutName() + "' WHERE workout_id = " + workout.getWorkoutId();

        try {

            conn.s.executeUpdate(query);

            ArrayList<Exercise> lstExercise = workout.getExercisesList();

            for(Exercise exercise : lstExercise) {

                query = "INSERT INTO exercise (exercise_name, workout_id) VALUES ('" + exercise.getExerciseName() + "', " + workout.getWorkoutId() + ")";

                conn.s.executeUpdate(query);

                query = "SELECT exercise_id FROM exercise WHERE exercise_name = '" + exercise.getExerciseName() + "' AND workout_id = " + workout.getWorkoutId();

                ResultSet rs = conn.s.executeQuery(query);

                if(rs.next()) {

                    int exerciseID = Integer.parseInt(rs.getString("exercise_id"));

                    int setCounter = 1;
                    ArrayList<Set> lstSet = exercise.getSetsList();

                    for(Set set : lstSet) {

                        query = "INSERT INTO sets (exercise_id, workout_id, sets_number, sets_reps, sets_weight) VALUES (" + exerciseID +
                                ", " + workout.getWorkoutId() + ", " + setCounter + ", " + set.getReps() + ", " + set.getWeight() + ")";

                        conn.s.executeUpdate(query);

                        setCounter++;
                    }
                }
            }
        } catch(Exception e) {

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
            table.setEnabled(false);

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