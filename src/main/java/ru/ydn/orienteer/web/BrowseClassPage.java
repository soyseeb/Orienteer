package ru.ydn.orienteer.web;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.annotation.mount.MountPath;

import ru.ydn.orienteer.components.commands.ODocumentCreateCommand;
import ru.ydn.orienteer.components.commands.DeleteCommand;
import ru.ydn.orienteer.components.table.OrienteerDataTable;
import ru.ydn.orienteer.services.IOClassIntrospector;
import ru.ydn.wicket.wicketorientdb.model.OClassModel;
import ru.ydn.wicket.wicketorientdb.model.OQueryDataProvider;

import com.google.inject.Inject;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.record.impl.ODocument;

@MountPath("/browse/${className}")
public class BrowseClassPage extends OrienteerBasePage<OClass>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private IOClassIntrospector oClassIntrospector;
	
	public BrowseClassPage(String className)
	{
		this(new OClassModel(className));
	}
	
	public BrowseClassPage(IModel<OClass> model)
	{
		super(model);
	}

	public BrowseClassPage(PageParameters parameters)
	{
		super(parameters);
	}

	@Override
	protected IModel<OClass> resolveByPageParameters(
			PageParameters pageParameters) {
		return new OClassModel(pageParameters.get("className").toOptionalString());
	}

	@Override
	public void initialize() {
		super.initialize();
		Form<ODocument> form = new Form<ODocument>("form");
		OQueryDataProvider<ODocument> provider = new OQueryDataProvider<ODocument>("select from "+getModelObject().getName())
			{
			/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				//To optimize number of queries
				@Override
				public long size() {
					return BrowseClassPage.this.getModelObject().count();
				}
			};
		
		OrienteerDataTable<ODocument, String> table = new OrienteerDataTable<ODocument, String>("table", oClassIntrospector.getColumnsFor(getModelObject()), provider, 20);
		table.addCommand(new ODocumentCreateCommand(table, getModel()));
		table.addCommand(new DeleteCommand(table));
		form.add(table);
		add(form);
	}

	@Override
	public IModel<String> getTitleModel() {
		return new StringResourceModel("class.browse.title", getModel());
	}
	

}
