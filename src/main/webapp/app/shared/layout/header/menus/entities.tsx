import React from 'react';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from '../header-components';

export const EntitiesMenu = props => (
  // tslint:disable-next-line:jsx-self-close
  <NavDropdown icon="th-list" name={translate('global.menu.entities.main')} id="entity-menu">
    <DropdownItem tag={Link} to="/entity/item">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;<Translate contentKey="global.menu.entities.item" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/item-cost">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;<Translate contentKey="global.menu.entities.itemCost" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/room">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;<Translate contentKey="global.menu.entities.room" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/reservation">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;<Translate contentKey="global.menu.entities.reservation" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/lending">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;<Translate contentKey="global.menu.entities.lending" />
    </DropdownItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
