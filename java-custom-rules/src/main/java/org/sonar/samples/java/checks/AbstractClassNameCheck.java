package org.sonar.samples.java.checks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.ClassTree;

/**
 * <p>描述：</p>
 * <p>业务域：</p>
 * <p>版本：</p>
 * <p>日期：2020/03/10 1:54 下午</p>
 * <p>作者：gfl</p>
 */
@Rule(key = "AbstractClassNameCheck_java.html")
public class AbstractClassNameCheck extends BaseTreeVisitor implements JavaFileScanner {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractClassNameCheck.class);

    private JavaFileScannerContext context;


    @Override
    public void scanFile(JavaFileScannerContext javaFileScannerContext) {
        this.context = javaFileScannerContext;
        scan(context.getTree());
    }

    @Override
    public void visitClass(ClassTree tree) {
        String className = tree.simpleName().name();
        LOGGER.info(className + "<<>>" + tree.symbol().isAbstract());
        if (tree.symbol().isAbstract()) {
            //判断名称是否以Abstract 或 Base 开头
            String abName = "Abstract";
            String bsName = "Base";
            //判断类名如果小于Abstract 或 Base
            if (className.length() < abName.length() || className.length() < bsName.length()) {
                context.reportIssue(this, tree, "The Name Of Abstract Class should use Abstract or Base first");
            } else {
                //判断是否存在 Abstract 或 Base
                if (!className.contains(abName)) {
                    if (!className.contains(bsName)) {
                        context.reportIssue(this, tree, "The Name Of Abstract Class should use Abstract or Base first");
                    } else {
                        if (className.indexOf(bsName) != 0) {
                            context.reportIssue(this, tree, "The Name Of Abstract Class should use Abstract or Base first");
                        }
                    }
                } else {
                    if (className.indexOf(abName) != 0) {
                        context.reportIssue(this, tree, "The Name Of Abstract Class should use Abstract or Base first");
                    }
                }
            }
        }
        super.visitClass(tree);
    }
}
