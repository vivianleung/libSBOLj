package org.sbolstandard.system;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.net.URI;

import org.sbolstandard.core.DnaComponent;
import org.sbolstandard.core.DnaSequence;
import org.sbolstandard.core.SBOLDocument;
import org.sbolstandard.core.SBOLFactory;
import org.sbolstandard.core.SequenceAnnotation;
import org.sbolstandard.core.StrandType;
import org.sbolstandard.core.rdf.CoreRdfPicklers;
import org.sbolstandard.system.impl.ContextImpl;
import org.sbolstandard.system.impl.DeviceImpl;
import org.sbolstandard.system.impl.ModelImpl;
import org.sbolstandard.system.impl.SBOLSystemImpl;
import org.sbolstandard.system.impl.SystemRdfPickler;

public class Test {

	public static void main1 (String[] args) throws Exception
	{
		ByteArrayOutputStream stream = new ByteArrayOutputStream();

		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stream));
		SBOLDocument document=GetSBOLDocument();
		CoreRdfPicklers.instance().getIO().write(document, writer);
		String rdfData = new String(stream.toString());		
		System.out.print(rdfData);
	}
	
	public static void main (String[] args) throws Exception
	{
		ByteArrayOutputStream stream = new ByteArrayOutputStream();

		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stream));
		SBOLDocument document=SBOLFactory.createDocument();
		SBOLSystem system=new SBOLSystemImpl();
		system.setURI(new URI("http://www.fake.org"));
		system.setName("sys name");
		system.setDescription("sys desc");
		system.setDisplayId("sys_id");
		Model model = new ModelImpl();
		model.setURI(new URI("http://www.fake.org.model"));
		model.setDisplayId("model_id");
		model.setName("model name");
		model.setSource(new URI("http://www.fake.org/sbml"));
		model.setFramework(new URI("ODE"));
		model.setLanguage(new URI("SBML"));
		
		model.setDescription("model description");
		
		system.AddModel(model);
		Context context=new ContextImpl();
		context.setURI(new URI("http://www.fake.org.context1"));
		context.addType(new URI("http://wwww.pur.org/context/bioenvironment/broth"));
		context.setName("Broth bioenvironment");
		context.setDisplayId("broth");
		context.setDescription("Broth bioenvironment");
		
		//context.setName("context1");
		system.addContext(context);
		
		Device device=new DeviceImpl();
		device.setURI(new URI("http://www.fake.org.device1"));
		device.setName("device name");
		device.setDisplayId("device id");
		device.setDescription("device description");
		
		
		device.addDnaComponent(GetDoublePromoter());
		system.addDevice(device);
		
		
		document.addContent(system);
				
		SystemRdfPickler.instance().getIO().write(document, writer);
		String rdfData = new String(stream.toString());		
		System.out.print(rdfData);
	}
	
	
	private static SBOLDocument GetSBOLDocument() throws Exception
	{
SBOLDocument document=SBOLFactory.createDocument();


			
		document.addContent(GetDoublePromoter());
		
		return document;
		
	}
	
	private static DnaComponent GetDoublePromoter() throws Exception
	{
		String baseURL="http://fakeuri.org";
		//DnaComponent prom1 = GetDnaComponent(baseURL + "/part/prom1", "prom1", "Promoter 1", "An example promoter", "http://purl.org/obo/owl/SO#SO_0000167","aaa");
				DnaComponent dnaComponent2 = GetDnaComponent(baseURL + "/part/doubleprom1", "doubleprom1", "Double Promoter 1", "A double promoter example", "http://purl.org/obo/owl/SO#SO_0000167","aaaaaa");
				DnaComponent prom1 = GetDnaComponent(baseURL + "/part/prom1", "prom1", "Promoter 1", "A promoter ", "http://purl.org/obo/owl/SO#SO_0000167","aaa");
				
				//SequenceAnnotation annotation1=GetSequenceAnnotation(baseURL + "/part/doubleprom1_1_3", 1, 3, StrandType.POSITIVE, prom1);
				//SequenceAnnotation annotation2=GetSequenceAnnotation(baseURL + "/part/doubleprom1_4_6", 4, 6, StrandType.POSITIVE, prom1);
				//dnaComponent2.addAnnotation(annotation1);
				//dnaComponent2.addAnnotation(annotation2);
			return dnaComponent2;
	}
	private static DnaComponent GetDnaComponent(String uri, String displayId, String name,String description, String type,String nucleotideSequence) throws Exception
	{
		DnaComponent dnaComponent = SBOLFactory.createDnaComponent();
		dnaComponent.setName(name);
		dnaComponent.setDescription(description);
		dnaComponent.setDisplayId(displayId);
		dnaComponent.setURI(new URI(uri));
		dnaComponent.addType(new URI(type));
		DnaSequence sequence=SBOLFactory.createDnaSequence();		
		sequence.setNucleotides(nucleotideSequence);		
		sequence.setURI(new URI(uri + "/NA"));			
		dnaComponent.setDnaSequence(sequence);
		return dnaComponent;
	}
	
	private static SequenceAnnotation GetSequenceAnnotation(String uri,int start,int end, StrandType strandType,DnaComponent dnaComponent) throws Exception
	{
		SequenceAnnotation annotation=SBOLFactory.createSequenceAnnotation();
		annotation.setURI(new URI(uri));
		annotation.setBioStart(start);
		annotation.setBioEnd(end);
		annotation.setStrand(strandType);
		annotation.setSubComponent(dnaComponent);
		
		return annotation;		
	}
	
}
