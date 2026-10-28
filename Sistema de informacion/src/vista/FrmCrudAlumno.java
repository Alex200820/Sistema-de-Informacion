package vista;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import entidad.Alumno;
import model.AlumnoModel;
import util.ValidateUtil;

public class FrmCrudAlumno extends JFrame implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtFechaNacimiento;
	private JTextField txtEmail;
	private JTextField txtDNI;
	private JTextField txtNombre;
	private JTextField txtCodigo;
	private JTable table;
	private JButton btnListar;
	private JButton btnBuscar;
	private JButton btnRegistrar;
	private JButton btnActualizar;
	private JButton btnEliminarLogico;
	private JButton btnEliminarFisico;
	private JButton btnLimpiar;
	private JCheckBox chkEstado;
	private JButton brnReporte;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
					FrmCrudAlumno frame = new FrmCrudAlumno();
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
	public FrmCrudAlumno() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 10)); // NORTE: título | CENTRO: trabajo | SUR: reporte

		// ============ NORTE: título ============
		JLabel lblMantenimientoAlumno = new JLabel("Mantenimiento Alumno");
		lblMantenimientoAlumno.setHorizontalAlignment(SwingConstants.CENTER);
		lblMantenimientoAlumno.setFont(new Font("Tahoma", Font.BOLD, 18));
		contentPane.add(lblMantenimientoAlumno, BorderLayout.NORTH);

		// ============ CENTRO: formulario + botones + tabla ============
		JPanel panelCentro = new JPanel(new BorderLayout(16, 0));
		contentPane.add(panelCentro, BorderLayout.CENTER);

		// --- OESTE: formulario con GridBagLayout (nada se desalinea jamás) ---
		JPanel panelForm = new JPanel(new GridBagLayout());
		panelCentro.add(panelForm, BorderLayout.WEST);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(4, 4, 4, 4);   // aire uniforme entre filas
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel lblCodigo = new JLabel("Código");
		gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
		panelForm.add(lblCodigo, gbc);

		txtCodigo = new JTextField();
		txtCodigo.setColumns(10);
		gbc.gridx = 1; gbc.weightx = 1.0;
		panelForm.add(txtCodigo, gbc);

		JLabel lblNombre = new JLabel("Nombre");
		gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
		panelForm.add(lblNombre, gbc);

		txtNombre = new JTextField();
		txtNombre.setColumns(10);
		gbc.gridx = 1; gbc.weightx = 1.0;
		panelForm.add(txtNombre, gbc);

		JLabel lblDNI = new JLabel("DNI");
		gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
		panelForm.add(lblDNI, gbc);

		txtDNI = new JTextField();
		txtDNI.setColumns(10);
		gbc.gridx = 1; gbc.weightx = 1.0;
		panelForm.add(txtDNI, gbc);

		JLabel lblEmail = new JLabel("Email");
		gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
		panelForm.add(lblEmail, gbc);

		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		gbc.gridx = 1; gbc.weightx = 1.0;
		panelForm.add(txtEmail, gbc);

		JLabel lblFechanacimiento = new JLabel("FechaNacimiento");
		gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0;
		panelForm.add(lblFechanacimiento, gbc);

		txtFechaNacimiento = new JTextField();
		txtFechaNacimiento.setColumns(10);
		gbc.gridx = 1; gbc.weightx = 1.0;
		panelForm.add(txtFechaNacimiento, gbc);

		chkEstado = new JCheckBox("Activo");
		gbc.gridx = 1; gbc.gridy = 5; gbc.weightx = 0;
		panelForm.add(chkEstado, gbc);

		// --- CENTRO: botonera apilada con aire (8px entre botones) ---
		JPanel panelBotones = new JPanel(new GridLayout(0, 1, 0, 8));
		panelCentro.add(panelBotones, BorderLayout.CENTER);

		btnListar = new JButton("Listar Todos");
		btnListar.addActionListener(this);
		panelBotones.add(btnListar);

		btnBuscar = new JButton("Busca"); // TODO: corregir a "Buscar"
		btnBuscar.setIcon(new ImageIcon("icons/004-buscar.png"));
		btnBuscar.addActionListener(this);
		panelBotones.add(btnBuscar);

		btnRegistrar = new JButton("Registrar");
		btnRegistrar.setIcon(new ImageIcon("icons/001-agregar.png"));
		btnRegistrar.addActionListener(this);
		panelBotones.add(btnRegistrar);

		btnActualizar = new JButton("Actualizar");
		btnActualizar.setIcon(new ImageIcon("icons/editar.png"));
		btnActualizar.addActionListener(this);
		panelBotones.add(btnActualizar);

		btnEliminarLogico = new JButton("Eliminar lógico");
		btnEliminarLogico.setIcon(new ImageIcon("icons/eliminar.png"));
		btnEliminarLogico.addActionListener(this);
		panelBotones.add(btnEliminarLogico);

		btnEliminarFisico = new JButton("Eliminar físico");
		btnEliminarFisico.setIcon(new ImageIcon("icons/remove.gif"));
		btnEliminarFisico.addActionListener(this);
		panelBotones.add(btnEliminarFisico);

		btnLimpiar = new JButton("Limpiar");
		btnLimpiar.setIcon(new ImageIcon("icons/005-limpiar.png"));
		btnLimpiar.addActionListener(this);
		panelBotones.add(btnLimpiar);

		// --- ESTE: tabla en scrollpane ---
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(691, 293));
		panelCentro.add(scrollPane, BorderLayout.EAST);

		table = new JTable();
		table.addMouseListener(this);
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "C\u00F3digo", "Nombres", "DNI", "Correo", "Fecha Nacimiento", "Estado" }));

		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(4).setPreferredWidth(50);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		table.setRowSelectionAllowed(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setDefaultEditor(Object.class, null);

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.CENTER);

		table.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
		table.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
		table.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);

		UIDefaults defaults = UIManager.getLookAndFeelDefaults();
		defaults.putIfAbsent("Table.alternateRowColor", new Color(176, 245, 215));

		scrollPane.setViewportView(table);

		// ============ SUR: botón reporte a la derecha ============
		JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		contentPane.add(panelSur, BorderLayout.SOUTH);

		brnReporte = new JButton("Reporte");
		brnReporte.setIcon(new ImageIcon("icons/005-reporte-de-negocios.png"));
		brnReporte.addActionListener(this);
		brnReporte.setFocusPainted(false);
		brnReporte.setIconTextGap(8);
		panelSur.add(brnReporte);

		listarTodos();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == brnReporte) {
			do_brnReporte_actionPerformed(e);
		}
		if (e.getSource() == btnLimpiar) {
			do_btnLimpiar_actionPerformed(e);
		}
		if (e.getSource() == btnEliminarFisico) {
			do_btnEliminarFisico_actionPerformed(e);
		}
		if (e.getSource() == btnEliminarLogico) {
			do_btnEliminarLogico_actionPerformed(e);
		}
		if (e.getSource() == btnActualizar) {
			do_btnActualizar_actionPerformed(e);
		}
		if (e.getSource() == btnRegistrar) {
			do_btnRegistrar_actionPerformed(e);
		}
		if (e.getSource() == btnBuscar) {
			do_btnBuscar_actionPerformed(e);
		}
		if (e.getSource() == btnListar) {
			do_btnListar_actionPerformed(e);
		}
	}

	protected void do_btnListar_actionPerformed(ActionEvent e) {
		listarTodos();
	}

	protected void do_btnBuscar_actionPerformed(ActionEvent e) {
		buscar();
	}

	protected void do_btnRegistrar_actionPerformed(ActionEvent e) {
		registrar();
		listarTodos();
		limpiar();
	}

	protected void do_btnActualizar_actionPerformed(ActionEvent e) {
		actualizar();
		listarTodos();
		limpiar();
	}

	protected void do_btnEliminarLogico_actionPerformed(ActionEvent e) {
		eliminarLogico();
		listarTodos();
		limpiar();
	}

	protected void do_btnEliminarFisico_actionPerformed(ActionEvent e) {
		eliminarFisico();
		listarTodos();
		limpiar();
	}

	protected void do_btnLimpiar_actionPerformed(ActionEvent e) {
		limpiar();
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == table) {
			do_table_mouseClicked(e);
		}
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	protected void do_table_mouseClicked(MouseEvent e) {
		seleccionarFila();
	}
	
	void seleccionarFila() {
		int fila = table.getSelectedRow();
		txtCodigo.setText(table.getValueAt(fila, 0).toString());
		txtNombre.setText(table.getValueAt(fila, 1).toString());
		txtDNI.setText(table.getValueAt(fila, 2).toString());
		txtEmail.setText(table.getValueAt(fila, 3).toString());
		txtFechaNacimiento.setText(table.getValueAt(fila, 4).toString());
		String estado = table.getValueAt(fila, 5).toString();
		if ("Activo".equalsIgnoreCase(estado)) {
			chkEstado.setSelected(true);
		}else {
			chkEstado.setSelected(false);
		}
	}
	
	void listarTodos() {
		DefaultTableModel dtm = (DefaultTableModel) table.getModel();
		dtm.setRowCount(0); // Limpiar la tabla antes de agregar nuevos datos

		AlumnoModel model = new AlumnoModel();
		List<Alumno> lista = model.listaTodos();
		for (Alumno a : lista) {
			Object[] rowData = { a.getIdAlumno(), 
								 a.getNombre(), 
								 a.getDni(), 
								 a.getCorreo(), 
								 a.getFechaNacimiento(),
								 a.getEstado()==1?"Activo":"Inactivo" };
			dtm.addRow(rowData);
		}
	}

	void buscar() {
		String codigo = txtCodigo.getText().trim();
		if (codigo.isEmpty()) {
		    JOptionPane.showMessageDialog(this, "Seleccione un alumno o ingrese un código válido");
		    return;
		}
		AlumnoModel model = new AlumnoModel();
		Alumno objAlumno  = model.buscaAlumno(Integer.parseInt(codigo));
		if (objAlumno == null ) {
			JOptionPane.showMessageDialog(this, "No existe el alumnos de código " + codigo);
			limpiar();
		    return;
		}
		txtCodigo.setText(String.valueOf(objAlumno.getIdAlumno()));
		txtNombre.setText(objAlumno.getNombre());
		txtDNI.setText(objAlumno.getDni());
		txtEmail.setText(objAlumno.getCorreo());
		txtFechaNacimiento.setText(objAlumno.getFechaNacimiento().format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE));
		chkEstado.setSelected(objAlumno.getEstado() == 1? true: false);
		
		DefaultTableModel dtm = (DefaultTableModel) table.getModel();
		dtm.setRowCount(0); // Limpiar la tabla antes de agregar nuevos datos
		
		Object[] rowData = { objAlumno.getIdAlumno(), 
				objAlumno.getNombre(), 
				objAlumno.getDni(), 
				objAlumno.getCorreo(), 
				objAlumno.getFechaNacimiento(),
				objAlumno.getEstado()==1?"Activo":"Inactivo" };
		dtm.addRow(rowData);
	}

	void registrar() {
		// 1 Recibir los datos del formulario en String
		String nombre = txtNombre.getText().trim();
		String dni = txtDNI.getText().trim();
		String email = txtEmail.getText().trim();
		String fechaNacimiento = txtFechaNacimiento.getText().trim();

		// 2 Validar los datos (opcional)
		if (nombre.matches(ValidateUtil.TEXTO_40) == false) {
			JOptionPane.showMessageDialog(this, "El nombre no es válido. Tiene que tener de 1 a 40 caracteres");
			return;
		}
		if (dni.matches(ValidateUtil.DNI) == false) {
			JOptionPane.showMessageDialog(this, "El DNI no es válido. Tiene que tener 8 dígitos");
			return;
		}
		if (email.matches(ValidateUtil.EMAIL) == false) {
			JOptionPane.showMessageDialog(this, "El email no es válido");
			return;
		}
		if (fechaNacimiento.matches(ValidateUtil.DATE_YYYY_MM_DD) == false) {
			JOptionPane.showMessageDialog(this,
					"La fecha de nacimiento no es válida. Tiene que tener el formato YYYY-MM-DD");
			return;
		}

		// 3 Crear el objeto Alumno
		Alumno obj = new Alumno();
		obj.setNombre(nombre);
		obj.setDni(dni);
		obj.setCorreo(email);
		obj.setFechaNacimiento(java.time.LocalDate.parse(fechaNacimiento));

		// 4 Crear el objeto AlumnoModel
		AlumnoModel model = new AlumnoModel();
		int salida = model.insertaAlumno(obj);

		// 5 Mostrar el resultado
		if (salida > 0) {
			JOptionPane.showMessageDialog(this, "Alumno registrado correctamente");
		} else {
			JOptionPane.showMessageDialog(this, "Error al registrar el alumno");
		}
	}

	void actualizar() {
		// 1 Recibir los datos del formulario en String
		String nombre = txtNombre.getText().trim();
		String dni = txtDNI.getText().trim();
		String email = txtEmail.getText().trim();
		String fechaNacimiento = txtFechaNacimiento.getText().trim();
		String codigo = txtCodigo.getText().trim();
		
		// 2 Validar los datos (opcional)
		if (codigo.isEmpty()) {
		    JOptionPane.showMessageDialog(this, "Seleccione un alumno o ingrese un código válido");
		    return;
		}
		if (nombre.matches(ValidateUtil.TEXTO_40) == false) {
			JOptionPane.showMessageDialog(this, "El nombre no es válido. Tiene que tener de 1 a 40 caracteres");
			return;
		}
		if (dni.matches(ValidateUtil.DNI) == false) {
			JOptionPane.showMessageDialog(this, "El DNI no es válido. Tiene que tener 8 dígitos");
			return;
		}
		if (email.matches(ValidateUtil.EMAIL) == false) {
			JOptionPane.showMessageDialog(this, "El email no es válido");
			return;
		}
		if (fechaNacimiento.matches(ValidateUtil.DATE_YYYY_MM_DD) == false) {
			JOptionPane.showMessageDialog(this,
					"La fecha de nacimiento no es válida. Tiene que tener el formato YYYY-MM-DD");
			return;
		}

		// 3 Crear el objeto Alumno
		Alumno obj = new Alumno();
		obj.setIdAlumno(Integer.parseInt(codigo));
		obj.setNombre(nombre);
		obj.setDni(dni);
		obj.setCorreo(email);
		obj.setFechaNacimiento(java.time.LocalDate.parse(fechaNacimiento));

		// 4 Crear el objeto AlumnoModel
		AlumnoModel model = new AlumnoModel();
		int salida = model.actualizaAlumno(obj);

		// 5 Mostrar el resultado
		if (salida > 0) {
			JOptionPane.showMessageDialog(this, "Alumno actualizado correctamente");
		} else {
			JOptionPane.showMessageDialog(this, "Error al actualizar el alumno");
		}
	}

	void eliminarLogico() {
		String codigo = txtCodigo.getText().trim();
		if (codigo.isEmpty()) {
		    JOptionPane.showMessageDialog(this, "Seleccione un alumno o ingrese un código válido");
		     return;
		}
		AlumnoModel model = new AlumnoModel();
		Alumno objAlumno  = model.buscaAlumno(Integer.parseInt(codigo));
		int newEstado = objAlumno.getEstado() == 0 ? 1 : 0;
		objAlumno.setEstado(newEstado);
		model.actualizaAlumno(objAlumno);
	}

	void eliminarFisico() {
		 String codigo = txtCodigo.getText().trim();
		 if (codigo.isEmpty()) {
		     JOptionPane.showMessageDialog(this, "Seleccione un alumno o ingrese un código válido");
		     return;
		 }
		 
		 int confirm = JOptionPane.showConfirmDialog(
			        this,
			        "¿Confirma eliminar el alumno con código " + codigo + "?",
			        "Confirmar eliminación",
			        JOptionPane.YES_NO_OPTION,
			        JOptionPane.WARNING_MESSAGE
				 );
		 if (confirm != JOptionPane.YES_OPTION) {
		        return;
		 }
		
		AlumnoModel model = new AlumnoModel();
		int salida = model.eliminaAlumno(Integer.parseInt(codigo));
		if (salida > 0) {
			JOptionPane.showMessageDialog(this, "Alumno eliminado correctamente");
		} else {
			JOptionPane.showMessageDialog(this, "Error al eliminar el alumno");
		}
	}

	void limpiar() {
		txtCodigo.setText("");
		txtNombre.setText("");
		txtDNI.setText("");
		txtEmail.setText("");
		txtFechaNacimiento.setText("");
	}
	protected void do_brnReporte_actionPerformed(ActionEvent e) {
		FrmDialogReporteAlumno dialog = new FrmDialogReporteAlumno(this, true);
		dialog.setLocationRelativeTo(this);
		dialog.setVisible(true);
		dialog.setModal(true);
	}
}
