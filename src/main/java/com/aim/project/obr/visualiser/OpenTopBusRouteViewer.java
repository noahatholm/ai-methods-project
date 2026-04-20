package main.java.com.aim.project.obr.visualiser;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.java.com.aim.project.obr.OBRDomain;
import com.aim.project.obr.instance.Location;
import com.aim.project.obr.interfaces.OBRInstanceInterface;
import com.aim.project.obr.interfaces.OBRSolutionInterface;

/**
 * @author Warren G Jackson
 * @since 1.0.0 (15/03/2026)
 */
public class OpenTopBusRouteViewer extends JFrame {

	private static final long serialVersionUID = -7066509516892991728L;

	private UAVPanel oPanel;

	private Color oPoIColor;

	private Color oTourColor;

	public OpenTopBusRouteViewer(OBRInstanceInterface oInstance, OBRDomain oProblem, Color oPoIColor, Color oTourColor) {

		this.oPoIColor = oPoIColor;
		this.oTourColor = oTourColor;
		this.oPanel = new UAVPanel(oInstance, oProblem);

		JFrame frame = new JFrame();

		frame.setTitle("OBR -- Open-top Bus Route Solution Visualiser [Not To Scale]");
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.add(this.oPanel);
		frame.setVisible(true);
	}

	class UAVPanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1295525707913147839L;

		private OBRInstanceInterface oInstance;

		private OBRDomain oProblem;

		public UAVPanel(OBRInstanceInterface oInstance, OBRDomain oProblem) {

			this.oInstance = oInstance;
			this.oProblem = oProblem;
		}

		int map(double d, double min_x, double max_x, long out_min, long out_max) {

			return (int) ((d - min_x) * (out_max - out_min) / (max_x - min_x) + out_min);
		}

		public void updateSolution(OBRSolutionInterface[] aoCurrentSolutions,
                                   OBRSolutionInterface[] aoCandidateSolutions,
                                   OBRSolutionInterface oBestSolution) {

			this.repaint();
		}

		LinkedList<Color> oColorStack = new LinkedList<Color>();

		private void drawDepot(Graphics g, int x, int y, int width, int height) {

			oColorStack.push(g.getColor());

			g.setColor(Color.WHITE);
			g.fillRect(x-width/2, y-height/2, width, height);

			g.setColor(Color.BLACK);

			g.drawLine(x-width/2, y-height/2,x+width/2, y+height/2);
			g.drawLine(x+width/2, y-height/2,x-width/2, y+height/2);

			g.setColor(oColorStack.pop());
		}

		public void drawOpenTopBusRoute(OBRDomain oProblem, Graphics g) {

			int[] rep = oProblem.getBestSolutionRepresentation();

			if (rep != null) {

				Location oBusDepotLocation = oProblem.getLoadedInstance().getLocationOfBusDepot();

				int width = getWidth();
				int height = getHeight();

				double max_x = Integer.MIN_VALUE;
				double max_y = Integer.MIN_VALUE;
				double min_x = Integer.MAX_VALUE;
				double min_y = Integer.MAX_VALUE;

				// find min and max x and y coordinates
				max_x = Math.max(max_x, oBusDepotLocation.x());
				max_y = Math.max(max_y, oBusDepotLocation.y());
				min_x = Math.min(min_x, oBusDepotLocation.x());
				min_y = Math.min(min_y, oBusDepotLocation.y());

				for (int i : rep) {

					Location l = oInstance.getLocationForPoI(rep[i]);
					max_x = Math.max(max_x, l.x());
					max_y = Math.max(max_y, l.y());
					min_x = Math.min(min_x, l.x());
					min_y = Math.min(min_y, l.y());
				}

				// draw bus depot to first PoI
				int x1, x2, y1, y2;
				Location l1 = oBusDepotLocation, l2 = oInstance.getLocationForPoI(rep[0]);
				x1 = map(l1.x(), min_x, max_x, 10, width - 10);
				x2 = map(l2.x(), min_x, max_x, 10, width - 10);
				y1 = height - map(l1.y(), min_y, max_y, 10, height - 10);
				y2 = height - map(l2.y(), min_y, max_y, 10, height - 10);

				g.setColor(Color.YELLOW);
				g.drawLine(x1, y1, x2, y2);

				g.setColor(oPoIColor);
				g.fillOval(x1 - 2, y1 - 2, 4, 4);

				// draw PoI routes
				for (int i = 0; i < rep.length - 1; i++) {

					l1 = oInstance.getLocationForPoI(rep[i]);
					l2 = oInstance.getLocationForPoI(rep[i + 1]);

					x1 = map(l1.x(), min_x, max_x, 10, width - 10);
					x2 = map(l2.x(), min_x, max_x, 10, width - 10);
					y1 = height - map(l1.y(), min_y, max_y, 10, height - 10);
					y2 = height - map(l2.y(), min_y, max_y, 10, height - 10);

					g.setColor(oTourColor);
					g.drawLine(x1, y1, x2, y2);

					g.setColor(oPoIColor);
					g.fillOval(x1 - 2, y1 - 2, 4, 4);
				}

				// draw route from last PoI to bus depot
				l1 = oInstance.getLocationForPoI(rep[rep.length - 1]);
				l2 = oBusDepotLocation;
				x1 = map(l1.x(), min_x, max_x, 10, width - 10);
				x2 = map(l2.x(), min_x, max_x, 10, width - 10);
				y1 = height - map(l1.y(), min_y, max_y, 10, height - 10);
				y2 = height - map(l2.y(), min_y, max_y, 10, height - 10);

				g.setColor(Color.YELLOW);
				g.drawLine(x1, y1, x2, y2);

				g.setColor(oPoIColor);
				g.setColor(oPoIColor);
				g.fillOval(x1 - 2, y1 - 2, 4, 4);

				drawDepot(g, x2, y2, 12, 12);

			} else {

				g.setColor(Color.WHITE);
				System.out.println("Unsupported");
				g.drawString("Unsupported solution representation...", (int) (0), (int) (getHeight() / 2.0));
			}
		}

		public void paintComponent(Graphics g) {

			super.paintComponent(g);

			int width = getWidth();
			int height = getHeight();

			g.setColor(Color.BLACK);
			g.fillRect(0, 0, width, height);

			if (oProblem != null) {
				this.drawOpenTopBusRoute(oProblem, g);
			}

			g.dispose();
		}
	}
}
