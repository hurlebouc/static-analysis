package annot;

import com.google.auto.service.AutoService;
import com.sun.source.tree.BlockTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.StatementTree;
import com.sun.source.util.Trees;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.util.LinkedHashSet;
import java.util.Set;

@AutoService(Processor.class)
public class AnnotProc extends AbstractProcessor {


    private Types typeUtils;
    private Elements elementUtils;
    private Filer filer;
    private Messager messager;
    private int cpt = 1;
    private Trees trees;

    @Override
    public synchronized void init(ProcessingEnvironment env){
        super.init(env);
        typeUtils = env.getTypeUtils();
        elementUtils = env.getElementUtils();
        filer = env.getFiler();
        messager = env.getMessager();
        trees = Trees.instance(env);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        messager.printMessage(Diagnostic.Kind.WARNING, String.format("[%d] annotations", cpt));
        for (TypeElement annotation : annotations) {
            messager.printMessage(Diagnostic.Kind.WARNING, annotation.toString());
        }
        messager.printMessage(Diagnostic.Kind.WARNING, String.format("[%d] roundEnv", cpt));
        messager.printMessage(Diagnostic.Kind.WARNING, roundEnv.toString());
        for (Element element : roundEnv.getElementsAnnotatedWith(StaticAnalysis.class)) {
            messager.printMessage(Diagnostic.Kind.WARNING, "annotated element with Methode " + element);
            ExecutableElement executableElement = (ExecutableElement) element;
            MethodTree tree = trees.getTree(executableElement);
            messager.printMessage(Diagnostic.Kind.WARNING, "tree " + tree);
            BlockTree body = tree.getBody();
            for (StatementTree statement : body.getStatements()) {
                messager.printMessage(Diagnostic.Kind.NOTE, "statement " + statement);
                messager.printMessage(Diagnostic.Kind.NOTE, "statement.getKind() " + statement.getKind());
            }

        }


        cpt++;
//        throw new RuntimeException("plop");
        return true; // Exit processing
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotataions = new LinkedHashSet<>();
//        annotataions.add(Fabrique.class.getCanonicalName());
//        annotataions.add(Autre.class.getCanonicalName());
        annotataions.add(StaticAnalysis.class.getCanonicalName());
        return annotataions;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Iterable<? extends Completion> getCompletions(Element element, AnnotationMirror annotation, ExecutableElement member, String userText) {
        return super.getCompletions(element, annotation, member, userText);
    }

    @Override
    public Set<String> getSupportedOptions() {
        return super.getSupportedOptions();
    }
}
