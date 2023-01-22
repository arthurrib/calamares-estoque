import { IProduto } from 'app/entities/produto/produto.model';
import { IFornecedor } from 'app/entities/fornecedor/fornecedor.model';
import { IPedido } from 'app/entities/pedido/pedido.model';

export interface IProdutoFornecedor {
  id: number;
  valor?: number | null;
  observacoes?: string | null;
  produto?: Pick<IProduto, 'id'> | null;
  fornecedor?: Pick<IFornecedor, 'id'> | null;
  produtoFornecedor?: Pick<IPedido, 'id'> | null;
}

export type NewProdutoFornecedor = Omit<IProdutoFornecedor, 'id'> & { id: null };
