package vista;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.time.LocalDate;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import entidad.Alumno;
import model.AlumnoModel;
import util.ValidateUtil;

public class FrmConsultaAlumno extends JFrame implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNombre;
	private JLabel lblDni;
	private JTextField txtDni;
	private JLabel lblCorreo;
	private JTextField txtCorreo;
	private JLabel lblDesde;
	private JTextField txtDesde;
	private JLabel lblHasta;
	private JTextField txtHasta;
	private JTable table;
	private JButton btnFiltrar;
	private JButton btnCancelar;

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
					FrmConsultaAlumno frame = new FrmConsultaAlumno();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrmConsultaAlumno() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 844, 462);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 10)); // NORTE: título+filtros | CENTRO: tabla

		// ============ NORTE: título + panel de filtros ============
		JPanel panelNorte = new JPanel(new BorderLayout(0, 10));
		contentPane.add(panelNorte, BorderLayout.NORTH);

		JLabel lblTitulo = new JLabel("Consulta de Alumno");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 18));
		panelNorte.add(lblTitulo, BorderLayout.NORTH);

		// --- filtros con GridBagLayout ---
		JPanel panelFiltros = new JPanel(new GridBagLayout());
		panelNorte.add(panelFiltros, BorderLayout.CENTER);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(4, 6, 4, 6);   // aire uniforme
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel lblNombre = new JLabel("Nombre");  
		gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
		panelFiltros.add(lblNombre, gbc);

		txtNombre = new JTextField();
		txtNombre.setColumns(10);
		gbc.gridx = 1; gbc.weightx = 1.0;
		panelFiltros.add(txtNombre, gbc);

		lblDni = new JLabel("DNI");
		gbc.gridx = 2; gbc.gridy = 0; gbc.weightx = 0;
		panelFiltros.add(lblDni, gbc);

		txtDni = new JTextField();
		txtDni.addKeyListener(this);
		txtDni.setColumns(10);
		gbc.gridx = 3; gbc.weightx = 1.0;
		panelFiltros.add(txtDni, gbc);

		lblCorreo = new JLabel("Correo");
		gbc.gridx = 4; gbc.gridy = 0; gbc.weightx = 0;
		panelFiltros.add(lblCorreo, gbc);

		txtCorreo = new JTextField();
		txtCorreo.setColumns(10);
		gbc.gridx = 5; gbc.weightx = 1.0;
		panelFiltros.add(txtCorreo, gbc);

		// fila 1: Fecha Nacimiento (desde) ... (hasta)
		lblDesde = new JLabel("Fecha Nacimiento (desde)");
		gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
		panelFiltros.add(lblDesde, gbc);

		txtDesde = new JTextField();
		txtDesde.setColumns(10);
		gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
		panelFiltros.add(txtDesde, gbc);

		lblHasta = new JLabel("(hasta)");
		gbc.gridx = 2; gbc.gridy = 1; gbc.weightx = 0;
		panelFiltros.add(lblHasta, gbc);

		txtHasta = new JTextField();
		txtHasta.setColumns(10);
		gbc.gridx = 3; gbc.gridy = 1; gbc.weightx = 1.0;
		panelFiltros.add(txtHasta, gbc);

		// fila 2: botones centrados
		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
		gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 6; gbc.weightx = 0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		panelFiltros.add(panelBotones, gbc);

		btnFiltrar = new JButton("Filtrar");
		btnFiltrar.addActionListener(this);
		panelBotones.add(btnFiltrar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this);
		panelBotones.add(btnCancelar);

		// ============ CENTRO: tabla ============
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"C\u00F3digo", "Nombre", "DNI", "Correo", "Fecha Nacimiento"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(4).setPreferredWidth(50);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		table.setRowSelectionAllowed(false);
		table.setDefaultEditor(Object.class, null);

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.CENTER);

		table.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
		table.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
		table.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);

		UIDefaults defaults = UIManager.getLookAndFeelDefaults();
		defaults.putIfAbsent("Table.alternateRowColor", new Color(176, 245, 215));

		scrollPane.setViewportView(table);
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCancelar) {
			do_btnCancelar_actionPerformed(e);
		}
		if (e.getSource() == btnFiltrar) {
			do_btnFiltrar_actionPerformed(e);
		}
	}
	protected void do_btnFiltrar_actionPerformed(ActionEvent e) {
		//1 Recibimos todos los parametros del formulario
		String nombre = txtNombre.getText();
		String dni = txtDni.getText();
		String correo = txtCorreo.getText();
		String desde = txtDesde.getText();
		String hasta = txtHasta.getText();
		
		//imprimir los parametros recibidos
		System.out.println("Parametros recibidos: ");
		System.out.println("Nombre: " + nombre);
		System.out.println("DNI: " + dni);
		System.out.println("Correo: " + correo);
		System.out.println("Desde: " + desde);
		System.out.println("Hasta: " + hasta);
		
		
		//2 Validacion
		if (!desde.isEmpty()  && desde.matches(ValidateUtil.DATE_YYYY_MM_DD) == false) {
			JOptionPane.showMessageDialog(this,"La fecha de nacimiento(Desde) no es válida. Tiene que tener el formato YYYY-MM-DD");
			return;
		}
		if (!hasta.isEmpty()  && hasta.matches(ValidateUtil.DATE_YYYY_MM_DD) == false) {
			JOptionPane.showMessageDialog(this,"La fecha de nacimiento(Hasta) no es válida. Tiene que tener el formato YYYY-MM-DD");
			return;
		}
		
		//Fecha desde debe ser menor o igual a fecha hasta
		if (!desde.isEmpty() && !hasta.isEmpty()) {
			LocalDate fechaDesde = LocalDate.parse(desde);
			LocalDate fechaHasta = LocalDate.parse(hasta);
			if (fechaDesde.isAfter(fechaHasta)) {
				JOptionPane.showMessageDialog(this,"La fecha de nacimiento(Desde) no puede ser mayor a la fecha de nacimiento(Hasta)");
				return;
			}
		}
		
		LocalDate fechaDesde = desde.isEmpty()? LocalDate.parse("9999-01-01"): LocalDate.parse(desde);
		LocalDate fechaHasta = hasta.isEmpty()? LocalDate.parse("9999-01-01"): LocalDate.parse(hasta);
		
		//3 Crear la clase model
		AlumnoModel objAlumnoModel = new AlumnoModel();
		List<Alumno> lista = objAlumnoModel.listaAlumno(nombre, dni, correo, fechaDesde, fechaHasta);
		
		//4 recorremos la lista
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0); // Limpiar la tabla antes de agregar nuevos datos
		
		for (Alumno a : lista) {
			Object[] rowData = { a.getIdAlumno(), 
								 a.getNombre(), 
								 a.getDni(), 
								 a.getCorreo(), 
								 a.getFechaNacimiento() 
								 };
			model.addRow(rowData);
		}
		
	}
	protected void do_btnCancelar_actionPerformed(ActionEvent e) {
		txtNombre.setText("");
		txtDni.setText("");
		txtCorreo.setText("");
		txtDesde.setText("");
		txtHasta.setText("");
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0); // Limpiar la tabla
	}
	public void keyPressed(KeyEvent e) {

	}
	public void keyReleased(KeyEvent e) {
		if (e.getSource() == txtDni) {
			do_txtDni_keyReleased(e);
		}
	}
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		System.out.println("Tecla presionada: keyPressed " + c);
		//Si es letra no ingresa
		if (Character.isLetter(c)) {
			e.consume(); // Ignorar la letra
		}
		//Si se ingresa más 8 dígitos digitos
		if (Character.isDigit(c) && txtDni.getText().length() >= 8) {
			e.consume(); // Ignorar el dígito si ya hay 8
		}
	}
	protected void do_txtDni_keyReleased(KeyEvent e) {
	}
}




