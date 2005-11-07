// $Header$
/*
 * Copyright 2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package org.apache.jmeter.report.gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;

import org.apache.jmeter.gui.util.HorizontalPanel;
import org.apache.jmeter.gui.util.ReportMenuFactory;
import org.apache.jmeter.gui.util.VerticalPanel;
import org.apache.jmeter.testelement.AbstractTable;
import org.apache.jmeter.testelement.LineGraph;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.gui.JLabeledChoice;
import org.apache.jorphan.gui.JLabeledTextField;

public class LineGraphGui extends AbstractReportGui {

    private JLabeledTextField xAxisLabel = 
        new JLabeledTextField(JMeterUtils.getResString("report_chart_x_axis_label"));
    
    private JLabeledTextField yAxisLabel = 
        new JLabeledTextField(JMeterUtils.getResString("report_chart_y_axis_label"));
    
    private JLabeledTextField caption = 
        new JLabeledTextField(JMeterUtils.getResString("report_chart_caption"),
                Color.white);

    private JLabeledTextField urls = 
        new JLabeledTextField(JMeterUtils.getResString("report_line_graph_urls"),
                Color.white);

    private JLabeledChoice checkItems = null;
	private JLabeledChoice xItems = null;

    public LineGraphGui() {
		super();
		init();
	}
	
	public String getLabelResource() {
		return "report_line_graph";
	}
	
	public JPopupMenu createPopupMenu() {
        JPopupMenu pop = new JPopupMenu();
        ReportMenuFactory.addFileMenu(pop);
        ReportMenuFactory.addEditMenu(pop,true);
        return pop;
	}

	protected void init() {
        setLayout(new BorderLayout(10, 10));
        setBorder(makeBorder());
        setBackground(Color.white);

        JPanel pane = new JPanel();
        pane.setLayout(new BorderLayout(10,10));
        pane.setBackground(Color.white);
        pane.add(this.getNamePanel(),BorderLayout.NORTH);
        
        VerticalPanel options = new VerticalPanel(Color.white);
        xAxisLabel.setBackground(Color.white);
        yAxisLabel.setBackground(Color.white);

        JLabel xLabel = new JLabel(JMeterUtils.getResString("report_chart_x_axis"));
		HorizontalPanel xpanel = new HorizontalPanel(Color.white);
		xLabel.setBorder(new EmptyBorder(5,2,5,2));
        xItems = new JLabeledChoice();
        xItems.setBackground(Color.white);
        xItems.setValues(AbstractTable.xitems);
        xpanel.add(xLabel);
        xpanel.add(xItems);
        options.add(xpanel);
        options.add(xAxisLabel);
        
		JLabel yLabel = new JLabel(JMeterUtils.getResString("report_chart_y_axis"));
		HorizontalPanel ypanel = new HorizontalPanel(Color.white);
		yLabel.setBorder(new EmptyBorder(5,2,5,2));
        checkItems = new JLabeledChoice();
        checkItems.setBackground(Color.white);
        ypanel.add(yLabel);
        ypanel.add(checkItems);
        options.add(ypanel);
        options.add(yAxisLabel);
        options.add(caption);
        options.add(urls);
        
        add(pane,BorderLayout.NORTH);
        add(options,BorderLayout.CENTER);
	}
	
	public TestElement createTestElement() {
		LineGraph element = new LineGraph();
		modifyTestElement(element);
		return element;
	}

	public void modifyTestElement(TestElement element) {
		this.configureTestElement(element);
		LineGraph bc = (LineGraph)element;
		bc.setXAxis(xItems.getText());
		bc.setYAxis(checkItems.getText());
		bc.setXLabel(xAxisLabel.getText());
		bc.setYLabel(yAxisLabel.getText());
        bc.setCaption(caption.getText());
        bc.setURLs(urls.getText());
	}
	
    public void configure(TestElement element) {
        super.configure(element);
        LineGraph bc = (LineGraph)element;
        xItems.setText(bc.getXAxis());
        checkItems.setText(bc.getYAxis());
        xAxisLabel.setText(bc.getXLabel());
        yAxisLabel.setText(bc.getYLabel());
        caption.setText(bc.getCaption());
        urls.setText(bc.getURLs());
        if (bc.getCheckedItems() != null && bc.getCheckedItems().size() > 0) {
        	String[] its = new String[bc.getCheckedItems().size()];
        	checkItems.setValues((String[])bc.getCheckedItems().toArray(its));
        }
    }
}
