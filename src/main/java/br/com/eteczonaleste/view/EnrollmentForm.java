package br.com.eteczonaleste.view;

import com.digitalpersona.onetouch.*;

import com.digitalpersona.onetouch.processing.*;
import java.awt.*;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class EnrollmentForm extends CaptureForm
{
	private DPFPEnrollment enroller = DPFPGlobal.getEnrollmentFactory().createEnrollment();
	
	EnrollmentForm(Frame owner) {
		super(owner);
	}
	
	@Override protected void init()
	{
		super.init();
		this.setTitle("Inscrição de impressão digital");
		updateStatus();
	}

	@Override protected void process(DPFPSample sample) {
		super.process(sample);
		// Process the sample and create a feature set for the enrollment purpose.
		DPFPFeatureSet features = extractFeatures(sample, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);

		// Check quality of the sample and add to enroller if it's good
		if (features != null) try
		{
			makeReport("As suas digitais foram criadas.");
			enroller.addFeatures(features);		// Add feature set to template.
		}
		catch (DPFPImageQualityException ex) { }
		finally {
			updateStatus();

			// Check if template has been created.
			switch(enroller.getTemplateStatus())
			{
				case TEMPLATE_STATUS_READY:	// report success and stop capturing
					stop();
					((MainForm)getOwner()).setTemplate(enroller.getTemplate());
					setPrompt("Clique em Fechar e clique em Verificação de Impressão Digital.");
					break;

				case TEMPLATE_STATUS_FAILED:	// report failure and restart capturing
					enroller.clear();
					stop();
					updateStatus();
					((MainForm)getOwner()).setTemplate(null);
					JOptionPane.showMessageDialog(EnrollmentForm.this, "O modelo de impressão digital não é válido. Repetir a inscrição da impressão digital.", "Inscri��o de impress�o digital", JOptionPane.ERROR_MESSAGE);
					start();
					break;
			}
		}
	}
	
	private void updateStatus()
	{
		// Show number of samples needed.
		setStatus(String.format("As Amostras das impressões digitais são necessárias: %1$s", enroller.getFeaturesNeeded()));
	}
	
}
