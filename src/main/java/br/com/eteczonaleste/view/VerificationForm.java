package br.com.eteczonaleste.view;

import java.awt.Color;
import java.awt.Frame;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;

import br.com.eteczonaleste.entity.Alunos;
import br.com.eteczonaleste.entity.Entregas;
import br.com.eteczonaleste.entityManager.AlunosManager;
import br.com.eteczonaleste.entityManager.EntregaManager;

public class VerificationForm<MemoryStream> extends CaptureForm {
	private DPFPVerification verificator = DPFPGlobal.getVerificationFactory().createVerification();
	public Collection<Alunos> alunosCadastrados;
	public Map<Alunos, DPFPVerificationResult> alunosComDigitalReconhecidaNaUltimaVerificacao;

	VerificationForm(Frame owner) {
		super(owner);	
		alunosCadastrados = new AlunosManager().findSelectAtivos();
		System.out.println("alunosCadastrados.size()="+alunosCadastrados.size());
	}

	@Override
	protected void init() {
		super.init();
		this.setTitle("Inscrição de impressão digital");
		updateStatus(0);
	}

	@Override
	protected void process(DPFPSample sample) {
		super.process(sample);

		// Process the sample and create a feature set for the enrollment purpose.
		DPFPFeatureSet features = extractFeatures(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);

		// Check quality of the sample and start verification if it's good
		if (features != null) {
			// Compare the feature set with our template
			DPFPVerificationResult result = null;
			DPFPTemplate template = null;
			
			// DPFPTemplate t = DPFPGlobal.getTemplateFactory().createTemplate();
			for (Alunos a : this.alunosCadastrados) {
				// result = verificator.verify(features, ((MainForm)getOwner()).getTemplate());
				//System.out.println("Verificando aluno : " + a.getNome());
				try {
					template = DPFPGlobal.getTemplateFactory().createTemplate();
					template.deserialize(a.getTemplate());

					result = verificator.verify(features, template);
					//System.out.println("result.isVerified() = " + result.isVerified());
					if (result.isVerified()) {						
						processaResultadoDaVerificacao(a);
						break;
					}
				} catch (Exception e) {		
					//e.printStackTrace();					
				}
				//updateStatus(result.getFalseAcceptRate());
				setBackground(Color.red);
			}
						
		}
	}

	private void processaResultadoDaVerificacao(Alunos a) {
		
//		if (alunosComDigitalReconhecidaNaUltimaVerificacao2.size() > 1) {
//			makeReport("ERRO: Mais de um aluno com a mesma digital");
//			Iterator<Alunos> iterator = alunosComDigitalReconhecidaNaUltimaVerificacao2.keySet().iterator();
//			String msg = "Alunos com digitais iguais: \n";
//			while (iterator.hasNext()) {
//				Alunos alunoComDigitalIgual = iterator.next();
//				msg += alunoComDigitalIgual.getNome() + ", ra:" + alunoComDigitalIgual.getRa() + "\n";
//			}
//			makeReport(msg);
//		} else if (alunosComDigitalReconhecidaNaUltimaVerificacao2.size() == 0) {
//			makeReport("DIGITAL NAO CORRESPONDE A NENHUMA PESSOA CADASTRADA");
//			setBackground(Color.red);
//			Toolkit.getDefaultToolkit().beep();
//		} else if (alunosComDigitalReconhecidaNaUltimaVerificacao2.size() == 1) {
//			Iterator<Alunos> iterator = alunosComDigitalReconhecidaNaUltimaVerificacao2.keySet().iterator();
//			Alunos alunoUnicoIdentificado = iterator.next();
		
			Entregas entrega = new Entregas();
			entrega.setData_retirada(new Date());
			entrega.setHora_retirada(new Date());
			entrega.setRa(a);
			EntregaManager entregaManager = new EntregaManager();

			//java.util.List entregasParaEsteAluno = entregaManager.verificaSeOAlunoJaAlmocou(alunoUnicoIdentificado);
			boolean jaAlmocou = entregaManager.verificaSeOAlunoJaAlmocou(a);
			System.out.println("boolean jaAlmocou "+jaAlmocou);
			if(jaAlmocou) {
				makeReport(a.getNome() + " - " + a.getRa() + " - ALUNO(A) JÁ ALMOÇOU");
				setBackground(Color.YELLOW);
//
//				/*try {
//					Clip clip = AudioSystem.getClip();
//					AudioInputStream inputStream = AudioSystem
//							.getAudioInputStream(VerificationForm.class.getResourceAsStream("sons/" + "beep-07.wav"));
//					clip.open(inputStream);
//					clip.start();
//					
//				} catch (Exception e) {
//					e.printStackTrace();
//				}*/
//
			} else {
				System.out.println("-----antes do insert");
				entregaManager.insert(entrega);
				System.out.println("depois do insert");
				makeReport(a.getNome() + " - " + a.getRa());
				setBackground(Color.green);
			}
	}

	private void updateStatus(int FAR) {
		// Show "False accept rate" value
		setStatus(String.format("Taxa de aceitação falsa (FAR) = %1$s", FAR));
	}

}
