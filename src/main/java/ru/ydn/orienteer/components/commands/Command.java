package ru.ydn.orienteer.components.commands;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;

import ru.ydn.orienteer.components.BootstrapType;
import ru.ydn.orienteer.components.FAIcon;
import ru.ydn.orienteer.components.FAIconType;
import ru.ydn.orienteer.components.IBootstrapTypeAware;
import ru.ydn.orienteer.components.structuretable.StructureTableCommandsToolbar;
import ru.ydn.orienteer.components.table.DataTableCommandsToolbar;


public abstract class Command extends Panel implements IBootstrapTypeAware
{
	private String icon;
	private AbstractLink link;
	private BootstrapType bootstrapType = BootstrapType.DEFAULT;
	
	public Command(IModel<?> labelModel, StructureTableCommandsToolbar toolbar)
    {
        this(toolbar.newChildId(), labelModel);
    }
	
    public Command(IModel<?> labelModel, DataTableCommandsToolbar toolbar)
    {
        this(toolbar.newChildId(), labelModel);
    }

    public Command(String labelKey)
    {
        this(labelKey, new ResourceModel(labelKey));
    }

    public Command(String commandId, String labelKey)
    {
        this(commandId, new ResourceModel(labelKey));
    }

    public Command(String commandId, IModel<?> labelModel)
    {
        super(commandId);
        initialize(commandId, labelModel);
    }
    
    protected void initialize(String commandId, IModel<?> labelModel)
    {
        link = newLink("command");
        link.setMarkupId(commandId.replace(".","_"));
        link.setOutputMarkupId(true);
        link.add(new AttributeAppender("class", new PropertyModel<String>(this, "bootstrapType.btnCssClass"), " "));
        link.add(new Label("label", labelModel).setRenderBodyOnly(true));
        link.add(new FAIcon("icon", new PropertyModel<String>(this, "icon")));
        add(link);
    }
    
    protected AbstractLink newLink(String id)
    {
    	return new Link<Object>(id)
        {
            public void onClick()
            {
                Command.this.onClick();
            }
        };
    }
    
    public String getIcon() {
		return icon;
	}

	public Command setIcon(String icon) {
		this.icon = icon;
		return this;
	}
	
	public Command setIcon(FAIconType type)
	{
		this.icon = type.getCssClass();
		return this;
	}

	AbstractLink getLink()
    {
    	return link;
    }
	

    @Override
	public Command setBootstrapType(BootstrapType type) {
    	this.bootstrapType = type;
		return this;
	}

	@Override
	public BootstrapType getBootstrapType() {
		return bootstrapType;
	}

	public abstract void onClick();
}