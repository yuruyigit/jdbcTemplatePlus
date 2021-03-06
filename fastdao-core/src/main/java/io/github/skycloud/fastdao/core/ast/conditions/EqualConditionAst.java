/**
 * @(#)EqualConditionAst.java, 11月 01, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.ast.conditions;

import com.google.common.collect.Lists;
import io.github.skycloud.fastdao.core.ast.SqlAst;
import io.github.skycloud.fastdao.core.ast.visitor.Visitor;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;

/**
 * @author yuntian
 */
@Getter
public
class EqualConditionAst implements EqualCondition, SqlAst {

    private String field;

    private Object value;

    public EqualConditionAst(String field, Object... value) {
        this.field = field;
        if (value.length == 1) {
            this.value = value[0];
        } else {
            this.value = Lists.newArrayList(value);
        }
    }

    public EqualConditionAst(String field, Collection value) {
        this.field = field;
        if (value == null || value.isEmpty()) {
            return;
        }
        if (value.size() == 1) {
            this.value = value.iterator().next();
        } else {
            this.value = value;
        }

    }

    @Override
    public boolean isLegal() {
        if (value instanceof Collection) {
            if (CollectionUtils.isEmpty((Collection) value)) {
                return false;
            }
        } else {
            return value != null;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public SqlAst copy() {
        return new EqualConditionAst(field, value);
    }


}