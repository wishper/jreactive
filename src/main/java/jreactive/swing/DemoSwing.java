package jreactive.swing;
import java.awt.Color;
import java.util.function.Function;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import jreactive.ReactiveFunction;
import jreactive.ReactiveProperty;
import jreactive.Reactivity;
public class DemoSwing {

	private JTextField t1,t2,t3,t4,t5;
	private ReactiveProperty<String> p1 = new ReactiveProperty<>("Ciao"),
			p2 = new ReactiveProperty<>("mondo");

	public static void main(String[] args) throws InterruptedException {
		new DemoSwing().testGui();
	}

	public void testGui() throws InterruptedException{
		JFrame f = createPlainOldSwing();

		bindText(t1, p1);
		bindText(t2, p2);

		ReactiveFunction<Void, String> reactiveFunction = new ReactiveFunction<>((a)->getP1() + ":" + p2.get());
		bindText(t4, reactiveFunction);

		Reactivity.watch(()->t3.setText(getP1() + " " + getP2()));

		Reactivity.watch(()->{
			if(reactiveFunction.apply(null).length()>10)
				t3.setBackground(Color.RED);
			else
				t3.setBackground(Color.GREEN);
		});

		bindBackground(t4, (a)->{
			if(reactiveFunction.apply(null).length()>10)
				return Color.RED;
			else
				return Color.BLUE;
		});

		bindText(t5, (a)->"Length: " + Integer.toString((getP1() + ":" + p2.get()).length()));

		while (f.isVisible()) {
			Thread.sleep(1000);
		}
	}

	private JFrame createPlainOldSwing() {
		JFrame f= new JFrame("TextField Example");
		t1=new JTextField();
		t1.setBounds(50,100, 200,30);

		t2=new JTextField();
		t2.setBounds(50,150, 200,30);

		t3=new JTextField();
		t3.setBounds(50,200, 200, 30);
		t3.setEditable(false);

		t4=new JTextField();
		t4.setBounds(50,250, 200,30);
		t4.setEditable(false);

		t5=new JTextField();
		t5.setBounds(50,300, 200,30);
		t5.setEditable(false);

		f.add(t1); f.add(t2); f.add(t3); f.add(t4); f.add(t5);

		f.setSize(400,400);
		f.setLayout(null);
		f.setVisible(true);

		return f;
	}

	private String getP1() {
		return p1.get();
	}

	private String getP2() {
		return p2.get();
	}

	public void bindText(JTextField f, ReactiveProperty<String> p){
		Reactivity.watch(()->SwingUtilities.invokeLater(()->f.setText(p.get())));
		f.getDocument().addDocumentListener((SimpleDocumentListener) e->p.set(f.getText()));
	}

	private void bindText(JTextField f, ReactiveFunction<Void, String> rf) {
		Reactivity.watch(()->{
			String t = rf.apply(null);
			SwingUtilities.invokeLater(()->f.setText(t));
		});
	}

	private void bindText(JTextField f, Function<Void, String> rf) {
		Reactivity.watch(()->{
			String t = new ReactiveFunction<>(rf).apply(null);
			SwingUtilities.invokeLater(()->f.setText(t));
		});
	}

	private void bindBackground(JTextField f, Function<Void, Color> rf) {
		Reactivity.watch(()->{
			Color c = new ReactiveFunction<>(rf).apply(null);
			SwingUtilities.invokeLater(()->f.setBackground(c));
		});
	}
}
