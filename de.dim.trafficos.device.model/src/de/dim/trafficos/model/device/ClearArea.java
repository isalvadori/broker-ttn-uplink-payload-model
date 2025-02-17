/*
 * Copyright (c) 2022 Contributors to the Eclipse Foundation.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Data In Motion - initial API and implementation 
 */
package de.dim.trafficos.model.device;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Clear Area</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.dim.trafficos.model.device.ClearArea#getType <em>Type</em>}</li>
 * </ul>
 *
 * @see de.dim.trafficos.model.device.TOSDevicePackage#getClearArea()
 * @model
 * @generated
 */
@ProviderType
public interface ClearArea extends EObject {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link de.dim.trafficos.model.device.ClearAreaType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see de.dim.trafficos.model.device.ClearAreaType
	 * @see #setType(ClearAreaType)
	 * @see de.dim.trafficos.model.device.TOSDevicePackage#getClearArea_Type()
	 * @model
	 * @generated
	 */
	ClearAreaType getType();

	/**
	 * Sets the value of the '{@link de.dim.trafficos.model.device.ClearArea#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see de.dim.trafficos.model.device.ClearAreaType
	 * @see #getType()
	 * @generated
	 */
	void setType(ClearAreaType value);

} // ClearArea
