package br.com.eteczonaleste.view;

import com.digitalpersona.onetouch.*;

import com.digitalpersona.onetouch.verification.*;

import br.com.eteczonaleste.entity.Alunos;
import br.com.eteczonaleste.entity.Entregas;
import br.com.eteczonaleste.entityManager.AlunosManager;
import br.com.eteczonaleste.entityManager.EntregaManager;

import java.awt.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.eclipse.jdt.internal.compiler.batch.Main;

public class VerificationForm<MemoryStream> extends CaptureForm {
	private DPFPVerification verificator = DPFPGlobal.getVerificationFactory().createVerification();
	public Collection<Alunos> alunosCadastrados;
	public Map<Alunos, DPFPVerificationResult> alunosComDigitalReconhecidaNaUltimaVerificacao;

	VerificationForm(Frame owner) {
		super(owner);
		alunosCadastrados = new AlunosManager().findSelectAll();
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
			this.alunosComDigitalReconhecidaNaUltimaVerificacao = new HashMap<Alunos, DPFPVerificationResult>();
			// DPFPTemplate t = DPFPGlobal.getTemplateFactory().createTemplate();
			for (Alunos a : this.alunosCadastrados) {
				// result = verificator.verify(features, ((MainForm)getOwner()).getTemplate());
				System.out.println("Verificando aluno : " + a.getNome());
				try {
					template = DPFPGlobal.getTemplateFactory().createTemplate();
					template.deserialize(a.getTemplate());

					result = verificator.verify(features, template);
					System.out.println("result.isVerified() = " + result.isVerified());
					if (result.isVerified()) {
						this.alunosComDigitalReconhecidaNaUltimaVerificacao.put(a, result);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				updateStatus(result.getFalseAcceptRate());
			}

			processaResultadoDaVerificacao(this.alunosComDigitalReconhecidaNaUltimaVerificacao);
		}
	}

	private void processaResultadoDaVerificacao(
			Map<Alunos, DPFPVerificationResult> alunosComDigitalReconhecidaNaUltimaVerificacao2) {
		System.out.println();

		if (alunosComDigitalReconhecidaNaUltimaVerificacao2.size() > 1) {
			makeReport("ERRO: Mais de um aluno com a mesma digital");
			Iterator<Alunos> iterator = alunosComDigitalReconhecidaNaUltimaVerificacao2.keySet().iterator();
			String msg = "Alunos com digitais iguais: \n";
			while (iterator.hasNext()) {
				Alunos alunoComDigitalIgual = iterator.next();
				msg += alunoComDigitalIgual.getNome() + ", ra:" + alunoComDigitalIgual.getRa() + "\n";
			}
			makeReport(msg);
		} else if (alunosComDigitalReconhecidaNaUltimaVerificacao2.size() == 0) {
			makeReport("DIGITAL NAO CORRESPONDE A NENHUMA PESSOA CADASTRADA");
			setBackground(Color.red);
			Toolkit.getDefaultToolkit().beep();
		} else if (alunosComDigitalReconhecidaNaUltimaVerificacao2.size() == 1) {

			Iterator<Alunos> iterator = alunosComDigitalReconhecidaNaUltimaVerificacao2.keySet().iterator();
			Alunos alunoUnicoIdentificado = iterator.next();

			Entregas entrega = new Entregas();
			entrega.setData_retirada(new Date());
			entrega.setHora_retirada(new Date());
			entrega.setRa(alunoUnicoIdentificado);
			EntregaManager entregaManager = new EntregaManager();

			java.util.List entregasParaEsteAluno = entregaManager.verificaSeOAlunoJaAlmocou(alunoUnicoIdentificado);
			System.out.println(entregasParaEsteAluno);

			if (entregasParaEsteAluno.size() == 0) {
				entregaManager.insert(entrega);
				makeReport(alunoUnicoIdentificado.getNome() + " - " + alunoUnicoIdentificado.getRa());
				setBackground(Color.green);

				/*try {
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem
							.getAudioInputStream(VerificationForm.class.getResourceAsStream("sons/" + "beep-07.wav"));
					clip.open(inputStream);
					clip.start();
					
				} catch (Exception e) {
					e.printStackTrace();
				}*/

			} else {
				makeReport(alunoUnicoIdentificado.getNome() + " - " + alunoUnicoIdentificado.getRa()
						+ " - ALUNO(A) JÁ ALMOÇOU");
				setBackground(Color.yellow);
			}
		}
	}

	private void updateStatus(int FAR) {
		// Show "False accept rate" value
		setStatus(String.format("Taxa de aceitação falsa (FAR) = %1$s", FAR));
	}

}
