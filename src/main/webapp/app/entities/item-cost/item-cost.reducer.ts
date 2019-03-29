import axios from 'axios';
import {
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IItemCost, defaultValue } from 'app/shared/model/item-cost.model';

export const ACTION_TYPES = {
  FETCH_ITEMCOST_LIST: 'itemCost/FETCH_ITEMCOST_LIST',
  FETCH_ITEMCOST: 'itemCost/FETCH_ITEMCOST',
  CREATE_ITEMCOST: 'itemCost/CREATE_ITEMCOST',
  UPDATE_ITEMCOST: 'itemCost/UPDATE_ITEMCOST',
  DELETE_ITEMCOST: 'itemCost/DELETE_ITEMCOST',
  RESET: 'itemCost/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IItemCost>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ItemCostState = Readonly<typeof initialState>;

// Reducer

export default (state: ItemCostState = initialState, action): ItemCostState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ITEMCOST_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ITEMCOST):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_ITEMCOST):
    case REQUEST(ACTION_TYPES.UPDATE_ITEMCOST):
    case REQUEST(ACTION_TYPES.DELETE_ITEMCOST):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_ITEMCOST_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ITEMCOST):
    case FAILURE(ACTION_TYPES.CREATE_ITEMCOST):
    case FAILURE(ACTION_TYPES.UPDATE_ITEMCOST):
    case FAILURE(ACTION_TYPES.DELETE_ITEMCOST):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_ITEMCOST_LIST):
      const links = parseHeaderForLinks(action.payload.headers.link);
      return {
        ...state,
        links,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links)
      };
    case SUCCESS(ACTION_TYPES.FETCH_ITEMCOST):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_ITEMCOST):
    case SUCCESS(ACTION_TYPES.UPDATE_ITEMCOST):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_ITEMCOST):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/item-costs';

// Actions

export const getEntities: ICrudGetAllAction<IItemCost> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ITEMCOST_LIST,
    payload: axios.get<IItemCost>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IItemCost> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ITEMCOST,
    payload: axios.get<IItemCost>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IItemCost> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ITEMCOST,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IItemCost> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ITEMCOST,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IItemCost> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ITEMCOST,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
